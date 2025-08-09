#!/usr/bin/env python3
"""
Simple script to run the Streamlit application
"""
import subprocess
import sys
import os

def main():
    """Run the Streamlit application"""
    try:
        # Set environment variables for better performance
        os.environ['STREAMLIT_SERVER_HEADLESS'] = 'true'
        os.environ['STREAMLIT_SERVER_ENABLE_CORS'] = 'false'
        os.environ['STREAMLIT_SERVER_ENABLE_XSRF_PROTECTION'] = 'false'
        
        # Run Streamlit
        cmd = [
            sys.executable, 
            '-m', 
            'streamlit', 
            'run', 
            'streamlit_app1.py',
            '--server.port', '8501',
            '--server.address', '0.0.0.0',
            '--server.headless', 'true'
        ]
        
        print("Starting Streamlit application...")
        print("Access the application at: http://localhost:8501")
        
        subprocess.run(cmd)
        
    except KeyboardInterrupt:
        print("\nStreamlit application stopped.")
    except Exception as e:
        print(f"Error starting Streamlit: {e}")

if __name__ == "__main__":
    main()