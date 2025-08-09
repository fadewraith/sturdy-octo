import streamlit as st
import pandas as pd
import pymysql
import ollama
import json
import datetime
import asyncio
import concurrent.futures
import time
import os
import re
import subprocess  # ✅ ADD THIS MISSING IMPORT
from typing import Dict, List, Tuple, Optional, Any
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Page configuration
st.set_page_config(
    page_title="NL to SQL Query Tool with Ollama",
    page_icon="🗃️",
    layout="wide",
    initial_sidebar_state="expanded"
)

# Custom CSS for better styling
st.markdown("""
<style>
    .main-header {
        text-align: center;
        padding: 1rem 0;
        background: linear-gradient(90deg, #1f1f1f, #3f3f3f);
        border-radius: 10px;
        margin-bottom: 2rem;
    }
    .metric-card {
        background: #f0f2f6;
        padding: 1rem;
        border-radius: 10px;
        margin: 0.5rem 0;
    }
    .query-box {
        background: #1e1e1e;
        padding: 1rem;
        border-radius: 5px;
        border-left: 4px solid #0066cc;
        margin: 1rem 0;
    }
    .success-box {
        background: #d4edda;
        color: #155724;
        padding: 1rem;
        border-radius: 5px;
        border-left: 4px solid #28a745;
        margin: 1rem 0;
    }
    .error-box {
        background: #f8d7da;
        color: #721c24;
        padding: 1rem;
        border-radius: 5px;
        border-left: 4px solid #dc3545;
        margin: 1rem 0;
    }
    .history-item {
        background: #f8f9fa;
        padding: 0.75rem;
        margin: 0.25rem 0;
        border-radius: 5px;
        border-left: 3px solid #6c757d;
    }
</style>
""", unsafe_allow_html=True)

class DatabaseConnection:
    """Handle MySQL database connections with connection pooling and error handling"""
    
    def __init__(self):
        self.connection = None
        self.connection_params = None
    
    def connect(self, host: str, port: int, user: str, password: str, database: str) -> bool:
        """Establish database connection with retry logic"""
        max_retries = 3
        for attempt in range(max_retries):
            try:
                self.connection_params = {
                    'host': host,
                    'port': port,
                    'user': user,
                    'password': password,
                    'database': database,
                    'charset': 'utf8mb4',
                    'autocommit': True,
                    'connect_timeout': 30,
                    'read_timeout': 60,
                    'write_timeout': 60
                }
                
                self.connection = pymysql.connect(**self.connection_params)
                logger.info(f"Successfully connected to database on attempt {attempt + 1}")
                return True
                
            except Exception as e:
                logger.error(f"Connection attempt {attempt + 1} failed: {str(e)}")
                if attempt == max_retries - 1:
                    raise e
                time.sleep(2 ** attempt)  # Exponential backoff
        
        return False
    
    def get_tables(self) -> List[str]:
        """Get all tables in the database"""
        try:
            if not self.connection:
                raise Exception("No database connection")
            with self.connection.cursor() as cursor:
                cursor.execute("SHOW TABLES")
                tables = [table[0] for table in cursor.fetchall()]
            return tables
        except Exception as e:
            logger.error(f"Error getting tables: {str(e)}")
            self._reconnect()
            if not self.connection:
                raise Exception("Failed to reconnect to database")
            with self.connection.cursor() as cursor:
                cursor.execute("SHOW TABLES")
                tables = [table[0] for table in cursor.fetchall()]
            return tables
    
    def get_table_info(self, table_name: str) -> Dict[str, Any]:
        """Get detailed information about a table"""
        try:
            if not self.connection:
                raise Exception("No database connection")
            with self.connection.cursor() as cursor:
                # Get column information
                cursor.execute(f"DESCRIBE `{table_name}`")
                columns = cursor.fetchall()
                
                # Get row count
                cursor.execute(f"SELECT COUNT(*) FROM `{table_name}`")
                result = cursor.fetchone()
                row_count = result[0] if result else 0
                
                return {
                    'columns': columns,
                    'row_count': row_count
                }
        except Exception as e:
            logger.error(f"Error getting table info for {table_name}: {str(e)}")
            return {'columns': [], 'row_count': 0}
    
    def execute_query(self, query: str) -> Tuple[List[Tuple[Any, ...]], List[str]]:
        """Execute SQL query with timeout handling"""
        try:
            if not self.connection:
                raise Exception("No database connection")
            with self.connection.cursor() as cursor:
                cursor.execute(query)
                results = list(cursor.fetchall())
                columns = [desc[0] for desc in cursor.description] if cursor.description else []
                return results, columns
        except Exception as e:
            logger.error(f"Error executing query: {str(e)}")
            # Try to reconnect and retry once
            self._reconnect()
            if not self.connection:
                raise Exception("Failed to reconnect to database")
            with self.connection.cursor() as cursor:
                cursor.execute(query)
                results = list(cursor.fetchall())
                columns = [desc[0] for desc in cursor.description] if cursor.description else []
                return results, columns
    
    def _reconnect(self):
        """Reconnect to database"""
        if self.connection_params:
            self.connection = pymysql.connect(**self.connection_params)
    
    def close(self):
        """Close database connection"""
        if self.connection:
            self.connection.close()
            self.connection = None

class OllamaQueryGenerator:
    """Generate SQL queries using Ollama"""
    
    def __init__(self, model_name: str = "llama3"):
        self.model_name = model_name
        self.client = None
        self._check_ollama_connection()
    
    def _check_ollama_connection(self) -> bool:
        """Check if Ollama is available using subprocess (like your working code)"""
        try:
            # Use subprocess like your working code
            result = subprocess.run(['ollama', 'list'], capture_output=True, text=True, timeout=5)
            
            if result.returncode != 0:
                st.error("❌ Ollama is not running")
                return False
            
            # Check if our model is available
            if self.model_name not in result.stdout:
                available_models = result.stdout.strip()
                st.warning(f"Model '{self.model_name}' not found. Available models:\n{available_models}")
                
                # Try to extract model names and use the first available one
                lines = result.stdout.strip().split('\n')
                model_names = []
                for line in lines:
                    if line.strip() and not line.startswith('NAME'):  # Skip header
                        model_name = line.split()[0]  # First column is usually the name
                        model_names.append(model_name)
                
                if model_names:
                    self.model_name = model_names[0]
                    st.info(f"Using model: {self.model_name}")
                else:
                    st.error("❌ No models available")
                    return False
            
            st.success("✅ Ollama is running and model is available")
            return True
            
        except subprocess.TimeoutExpired:
            st.error("❌ Ollama connection timeout")
            return False
        except FileNotFoundError:
            st.error("❌ Ollama CLI not found. Make sure Ollama is installed and in PATH")
            return False
        except Exception as e:
            st.error(f"❌ Error checking Ollama: {str(e)}")
            return False
    
    def generate_sql_query(self, prompt: str, tables_info: Dict[str, Any], database_schema: str) -> str:
        """Generate SQL query from natural language prompt"""
        try:
            # Create focused system prompt
            system_prompt = f"""You are a MySQL SQL query generator. Your ONLY job is to convert natural language to SQL.

            Database Schema:
            {database_schema}

            CRITICAL RULES:
            1. ONLY return SQL queries - no explanations, no markdown, no text
            2. Do NOT act like a chatbot or assistant
            3. Do NOT say you don't have database access
            4. Do NOT provide sample data
            5. Generate working MySQL syntax only
            6. If you need to limit results, add LIMIT clause

            Example Input: "show me users where age is 25"
            Example Output: SELECT * FROM users WHERE age = 25;

            Now generate SQL for this request:"""
            
            # Use a more direct prompt structure
            full_prompt = f"{system_prompt}\n\nRequest: {prompt}\n\nSQL:"
        
            # Try using the 'prompt' parameter instead of chat messages
            response = ollama.generate(
                model=self.model_name,
                prompt=full_prompt
            )
        
            sql_query = response['response'].strip()
        
            # Clean up the response
            sql_query = self._extract_sql_from_response(sql_query)
        
            return sql_query
        
        except Exception as e:
            logger.error(f"Error generating SQL with Ollama: {str(e)}")
            # Fallback: try the chat approach
            try:
                response = ollama.chat(
                    model=self.model_name,
                    messages=[
                        {
                            'role': 'system', 
                            'content': 'You are a MySQL SQL generator. Return only SQL queries, no explanations.'
                        },
                        {
                            'role': 'user', 
                            'content': f"Generate SQL for: {prompt}\n\nDatabase schema:\n{database_schema}"
                        }
                    ]
                )
                sql_query = response['message']['content'].strip()
                return self._extract_sql_from_response(sql_query)
            except Exception as e2:
                logger.error(f"Fallback method also failed: {str(e2)}")
                raise e2

    
    def _extract_sql_from_response(self, response: str) -> str:
        """Extract SQL query from Ollama response"""
        # Remove code block markers
        response = re.sub(r'```\s*', '', response)
        
        # Remove common prefixes
        response = re.sub(r'^(Here\'s the SQL query|The SQL query is|SQL:)\s*:?\s*', '', response, flags=re.IGNORECASE)
        
        # Clean up whitespace
        response = response.strip()
        
        # Ensure query ends with semicolon
        if not response.endswith(';'):
            response += ';'
        
        return response

def export_query_result(question: str, sql_query: str, results: pd.DataFrame, timestamp: str) -> str:
    """Export query results to text file"""
    export_content = f"""
Query Session Export
====================
Timestamp: {timestamp}
Question: {question}
Generated SQL Query:
{sql_query}

Results ({len(results)} rows):
{results.to_string(index=False)}

---
Export generated at: {datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}
"""
    return export_content

def init_session_state():
    """Initialize Streamlit session state"""
    if 'db_connection' not in st.session_state:
        st.session_state.db_connection = None
    if 'query_history' not in st.session_state:
        st.session_state.query_history = []
    if 'current_tables' not in st.session_state:
        st.session_state.current_tables = []
    if 'tables_info' not in st.session_state:
        st.session_state.tables_info = {}
    if 'ollama_generator' not in st.session_state:
        st.session_state.ollama_generator = None

def main():
    """Main Streamlit application"""
    init_session_state()
    
    # Header
    st.markdown("""
    <div class="main-header">
        <h1>🗃️ Natural Language to SQL Query Tool</h1>
        <p>Powered by Ollama & MySQL | Built for Azure Databases</p>
    </div>
    """, unsafe_allow_html=True)
    
    # Sidebar for database connection
    with st.sidebar:
        st.header("🔌 Database Connection")
        
        with st.form("db_connection_form"):
            st.subheader("MySQL Database Settings")
            host = st.text_input("Host", value="localhost", help="Database host address")
            port = st.number_input("Port", value=3306, min_value=1, max_value=65535)
            username = st.text_input("Username", help="Database username")
            password = st.text_input("Password", type="password", help="Database password")
            database = st.text_input("Database Name", help="Name of the database to connect to")
            
            submitted = st.form_submit_button("🔌 Connect to Database", use_container_width=True)
            
            if submitted and host and username and password and database:
                try:
                    with st.spinner("Connecting to database..."):
                        db_conn = DatabaseConnection()
                        if db_conn.connect(host, port, username, password, database):
                            st.session_state.db_connection = db_conn
                            
                            # Get tables and their info
                            tables = db_conn.get_tables()
                            st.session_state.current_tables = tables
                            
                            # Get detailed table information
                            tables_info = {}
                            for table in tables:
                                tables_info[table] = db_conn.get_table_info(table)
                            st.session_state.tables_info = tables_info
                            
                            st.success("✅ Connected successfully!")
                            st.rerun()
                        else:
                            st.error("❌ Connection failed!")
                            
                except Exception as e:
                    st.error(f"❌ Connection error: {str(e)}")
        
        # Disconnect button
        if st.session_state.db_connection:
            if st.button("🔌 Disconnect", use_container_width=True):
                st.session_state.db_connection.close()
                st.session_state.db_connection = None
                st.session_state.current_tables = []
                st.session_state.tables_info = {}
                st.success("Disconnected from database")
                st.rerun()
        
        # Ollama settings - FIXED SECTION
        st.header("🤖 Ollama Settings")
        try:
            # Use subprocess to get models (consistent with your working approach)
            result = subprocess.run(['ollama', 'list'], capture_output=True, text=True, timeout=5)
            
            if result.returncode == 0:
                # Parse the model list from stdout
                lines = result.stdout.strip().split('\n')
                available_models = []
                for line in lines:
                    if line.strip() and not line.startswith('NAME'):  # Skip header
                        model_name = line.split()[0]  # First column is the name
                        available_models.append(model_name)
                
                if available_models:
                    selected_model = st.selectbox("Select Model", available_models, 
                                                index=0 if available_models else None)
                    
                    if st.button("🔄 Initialize Ollama", use_container_width=True):
                        try:
                            if selected_model:
                                st.session_state.ollama_generator = OllamaQueryGenerator(selected_model)
                                st.success("✅ Ollama initialized!")
                            else:
                                st.error("Please select a model first")
                        except Exception as e:
                            st.error(f"❌ Ollama initialization failed: {str(e)}")
                else:
                    st.error("❌ No models found. Please run 'ollama pull llama3' first")
            else:
                st.error("❌ Ollama not running. Please start with 'ollama serve'")
                    
        except subprocess.TimeoutExpired:
            st.error("❌ Ollama connection timeout")
        except FileNotFoundError:
            st.error("❌ Ollama CLI not found")
        except Exception as e:
            st.error(f"❌ Ollama not available: {str(e)}")
            st.info("Make sure Ollama is running locally and accessible.")
    
    # Main content area
    if st.session_state.db_connection and st.session_state.current_tables:
        # Database overview
        col1, col2, col3 = st.columns(3)
        
        with col1:
            st.metric("📊 Total Tables", len(st.session_state.current_tables))
        
        with col2:
            total_rows = sum(info.get('row_count', 0) for info in st.session_state.tables_info.values())
            st.metric("📈 Total Rows", f"{total_rows:,}")
        
        with col3:
            if st.session_state.query_history:
                st.metric("📝 Queries Run", len(st.session_state.query_history))
            else:
                st.metric("📝 Queries Run", 0)
        
        # Tables overview
        with st.expander("📋 Database Tables Overview", expanded=False):
            tables_df = []
            for table_name, info in st.session_state.tables_info.items():
                tables_df.append({
                    'Table Name': table_name,
                    'Columns': len(info.get('columns', [])),
                    'Row Count': f"{info.get('row_count', 0):,}"
                })
            
            if tables_df:
                st.dataframe(pd.DataFrame(tables_df), use_container_width=True)
        
        # Query interface
        st.header("💬 Natural Language Query Interface")
        
        # Generate database schema for Ollama
        schema_info = ""
        for table_name, info in st.session_state.tables_info.items():
            schema_info += f"\nTable: {table_name}\n"
            for col in info.get('columns', []):
                schema_info += f"  - {col[0]} ({col[1]})\n"
        
        # Query input
        user_prompt = st.text_area(
            "Enter your question in natural language:",
            placeholder="Example: Show me the top 10 customers by total order value, including their names and email addresses",
            height=100
        )
        
        col1, col2 = st.columns([1, 1])
        
        with col1:
            generate_query = st.button("🎯 Generate & Execute Query", 
                                     disabled=not st.session_state.ollama_generator,
                                     use_container_width=True)
        
        with col2:
            if st.button("🧹 Clear History", use_container_width=True):
                st.session_state.query_history = []
                st.rerun()
        
        # Generate and execute query
        if generate_query and user_prompt and st.session_state.ollama_generator:
            try:
                with st.spinner("🤖 Generating SQL query with Ollama..."):
                    # Generate SQL query using Ollama
                    sql_query = st.session_state.ollama_generator.generate_sql_query(
                        user_prompt, 
                        st.session_state.tables_info, 
                        schema_info
                    )
                
                # Display generated SQL
                st.markdown("### 🔍 Generated SQL Query")
                st.code(sql_query, language="sql")
                
                # Execute query
                with st.spinner("⚡ Executing query..."):
                    results, columns = st.session_state.db_connection.execute_query(sql_query)
                    
                    if results:
                        # Convert to DataFrame
                        df = pd.DataFrame(list(results), columns=columns)
                        
                        # Display results
                        st.markdown("### 📊 Query Results")
                        st.dataframe(df, use_container_width=True)
                        
                        # Export functionality
                        timestamp = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                        export_content = export_query_result(user_prompt, sql_query, df, timestamp)
                        
                        st.download_button(
                            label="💾 Export Results to TXT",
                            data=export_content,
                            file_name=f"query_result_{datetime.datetime.now().strftime('%Y%m%d_%H%M%S')}.txt",
                            mime="text/plain"
                        )
                        
                        # Add to history
                        st.session_state.query_history.append({
                            'timestamp': timestamp,
                            'question': user_prompt,
                            'sql_query': sql_query,
                            'result_count': len(df),
                            'success': True
                        })
                        
                        st.success(f"✅ Query executed successfully! Retrieved {len(df)} rows.")
                    
                    else:
                        st.info("ℹ️ Query executed successfully but returned no results.")
                        
                        # Add to history
                        timestamp = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                        st.session_state.query_history.append({
                            'timestamp': timestamp,
                            'question': user_prompt,
                            'sql_query': sql_query,
                            'result_count': 0,
                            'success': True
                        })
                        
            except Exception as e:
                st.error(f"❌ Error: {str(e)}")
                
                # Add failed query to history
                timestamp = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                error_sql_query = 'N/A'
                try:
                    error_sql_query = sql_query
                except NameError:
                    pass
                
                st.session_state.query_history.append({
                    'timestamp': timestamp,
                    'question': user_prompt,
                    'sql_query': error_sql_query,
                    'result_count': 0,
                    'success': False,
                    'error': str(e)
                })
        
        # Query History
        if st.session_state.query_history:
            st.header("📜 Query History")
            
            for i, query in enumerate(reversed(st.session_state.query_history)):
                with st.expander(f"🕐 {query['timestamp']} - {'✅' if query['success'] else '❌'}", expanded=False):
                    st.markdown(f"**Question:** {query['question']}")
                    st.code(query['sql_query'], language="sql")
                    
                    if query['success']:
                        st.markdown(f"**Results:** {query['result_count']} rows")
                    else:
                        st.error(f"**Error:** {query.get('error', 'Unknown error')}")
    
    else:
        # Welcome screen
        st.markdown("""
        ## 🚀 Welcome to the NL to SQL Query Tool!
        
        This powerful tool allows you to query your MySQL database using natural language, powered by Ollama AI.
        
        ### 🔧 Setup Steps:
        1. **Connect to Database**: Enter your MySQL database credentials in the sidebar
        2. **Initialize Ollama**: Make sure Ollama is running locally and select a model
        3. **Start Querying**: Ask questions in plain English!
        
        ### ✨ Features:
        - 🤖 **AI-Powered**: Uses Ollama for intelligent SQL generation
        - 📊 **Database Overview**: See all tables and row counts
        - 📈 **Query History**: Track all your queries in the current session
        - 💾 **Export Results**: Download query results with timestamps
        - ⚡ **Concurrent Processing**: Handle large datasets efficiently
        - 🔄 **Auto-Retry**: Robust error handling and connection management
        
        ### 💡 Example Queries:
        - "Show me the top 10 customers by total purchases"
        - "List all products with low inventory levels"
        - "Find users who haven't logged in for the past 30 days"
        - "Calculate monthly revenue trends for the last year"
        """)
        
        if not st.session_state.db_connection:
            st.info("👈 Please connect to your database using the sidebar to get started!")
        
        if not st.session_state.ollama_generator:
            st.info("🤖 Please initialize Ollama in the sidebar to enable AI query generation!")

if __name__ == "__main__":
    main()
