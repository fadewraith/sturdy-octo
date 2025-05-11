// Comprehensive Reverse Engineering Curriculum
const curriculumModules = [
    {
        title: "1. Introduction to Reverse Engineering",
        level: "beginner",
        description: "Understand the fundamentals, ethics, and applications of reverse engineering.",
        concepts: [
            "Definition and history of reverse engineering",
            "Legal and ethical considerations",
            "Applications in security, malware analysis, interoperability",
            "Black box vs. white box approaches",
            "Static vs. dynamic analysis"
        ],
        exercises: [
            "Research and document different fields where reverse engineering is applied",
            "Set up a basic lab environment with virtualization software",
            "Review case studies of notable reverse engineering projects"
        ],
        resources: [
            {
                title: "The Ethics of Reverse Engineering",
                url: "https://www.eff.org/issues/coders/reverse-engineering-faq"
            },
            {
                title: "Setting Up a Safe Reverse Engineering Environment",
                url: "https://www.virtualbox.org/"
            }
        ]
    },
    {
        title: "2. Computer Architecture Fundamentals",
        level: "beginner",
        description: "Learn essential computer architecture concepts that form the foundation for reverse engineering.",
        concepts: [
            "CPU architecture and instruction sets (x86, x64, ARM)",
            "Memory organization and addressing",
            "Registers and their purposes",
            "Stack and heap concepts",
            "Binary, hexadecimal, and assembly language basics"
        ],
        exercises: [
            "Convert between decimal, binary, and hexadecimal number systems",
            "Identify key components in a system architecture diagram",
            "Study and document the purpose of common CPU registers"
        ],
        resources: [
            {
                title: "Computer Systems: A Programmer's Perspective (book)",
                url: "https://csapp.cs.cmu.edu/"
            },
            {
                title: "x86 Assembly Guide",
                url: "https://www.cs.virginia.edu/~evans/cs216/guides/x86.html"
            }
        ]
    },
    {
        title: "3. Assembly Language Basics",
        level: "beginner",
        type: "software",
        description: "Master reading and understanding assembly language, the foundation of software reverse engineering.",
        concepts: [
            "Common assembly instructions and their operations",
            "Control flow (jumps, calls, comparisons)",
            "Function calling conventions",
            "Common patterns in assembly code",
            "Translating high-level constructs to assembly"
        ],
        exercises: [
            "Write simple programs in assembly language",
            "Identify common programming constructs in disassembled code (loops, if statements)",
            "Trace the execution flow of a simple function in assembly"
        ],
        resources: [
            {
                title: "Assembly Language for x86 Processors (book)",
                url: "https://www.pearson.com/us/higher-education/program/Irvine-Assembly-Language-for-x-86-Processors-7th-Edition/PGM334393.html"
            },
            {
                title: "Compiler Explorer - See how code compiles to assembly",
                url: "https://godbolt.org/"
            }
        ]
    },
    {
        title: "4. Basic Software Reverse Engineering Tools",
        level: "beginner",
        type: "software",
        description: "Learn to use essential tools for software reverse engineering.",
        concepts: [
            "Disassemblers and their features",
            "Decompilers and their limitations",
            "Debuggers and breakpoints",
            "Binary format analysis tools",
            "Hex editors"
        ],
        exercises: [
            "Install and configure Ghidra, a free reverse engineering tool",
            "Use a hex editor to examine file formats",
            "Disassemble a simple program and analyze its structure",
            "Use a debugger to trace program execution"
        ],
        resources: [
            {
                title: "Ghidra National Security Agency Reverse Engineering Tool",
                url: "https://ghidra-sre.org/"
            },
            {
                title: "Introduction to x64dbg",
                url: "https://x64dbg.com/"
            },
            {
                title: "HxD Hex Editor",
                url: "https://mh-nexus.de/en/hxd/"
            }
        ]
    },
    {
        title: "5. File Formats and Executable Analysis",
        level: "intermediate",
        type: "software",
        description: "Understand common file formats and how to analyze executable programs.",
        concepts: [
            "PE (Windows) and ELF (Linux) file formats",
            "Headers, sections, and segments",
            "Import and export tables",
            "Dynamic linking and loading",
            "Symbol tables and debugging information"
        ],
        exercises: [
            "Map sections of a PE/ELF file to their purpose",
            "Identify imported and exported functions in a binary",
            "Analyze how dynamic libraries are loaded",
            "Locate and extract resources from an executable"
        ],
        resources: [
            {
                title: "Practical Binary Analysis (book)",
                url: "https://practicalbinaryanalysis.com/"
            },
            {
                title: "PE Explorer Tool",
                url: "http://www.pe-explorer.com/"
            }
        ]
    },
    {
        title: "6. Reversing Basic Algorithms and Data Structures",
        level: "intermediate",
        type: "software",
        description: "Learn to identify common algorithms and data structures in disassembled code.",
        concepts: [
            "Recognizing loops and array operations",
            "Identifying linked lists, trees and graphs",
            "Common cryptographic algorithms",
            "String operations and data manipulation",
            "Reconstructing complex data structures"
        ],
        exercises: [
            "Identify sorting algorithms in disassembled code",
            "Reconstruct custom data structures from memory patterns",
            "Trace the execution of a hashing or encryption function",
            "Implement a tool to extract and visualize data structures"
        ],
        resources: [
            {
                title: "Reverse Engineering for Beginners (free book)",
                url: "https://beginners.re/"
            },
            {
                title: "Practical Malware Analysis (book)",
                url: "https://nostarch.com/malware"
            }
        ]
    },
    {
        title: "7. Dynamic Analysis Techniques",
        level: "intermediate",
        type: "software",
        description: "Master techniques for analyzing programs during execution.",
        concepts: [
            "Debugging workflows and strategies",
            "Memory dumps and analysis",
            "API hooks and function interception",
            "Tracing program execution",
            "Analyzing network communication"
        ],
        exercises: [
            "Set up API monitoring tools to analyze program behavior",
            "Perform memory forensics on a running application",
            "Use breakpoints to extract encryption keys or algorithms",
            "Monitor and intercept network traffic from an application"
        ],
        resources: [
            {
                title: "Process Monitor",
                url: "https://docs.microsoft.com/en-us/sysinternals/downloads/procmon"
            },
            {
                title: "API Monitor",
                url: "http://www.rohitab.com/apimonitor"
            },
            {
                title: "Frida Dynamic Instrumentation Toolkit",
                url: "https://frida.re/"
            }
        ]
    },
    {
        title: "8. Introduction to Hardware Reverse Engineering",
        level: "intermediate",
        type: "hardware",
        description: "Learn the basics of hardware reverse engineering and circuit analysis.",
        concepts: [
            "Electronic components and their functions",
            "Circuit board analysis",
            "Signal tracing and measurement",
            "Communication protocols (I2C, SPI, UART)",
            "Firmware extraction techniques"
        ],
        exercises: [
            "Identify components on a simple circuit board",
            "Use a multimeter to trace connections and signals",
            "Extract and analyze firmware from a simple device",
            "Document a hardware system's architecture based on observation"
        ],
        resources: [
            {
                title: "The Hardware Hacker (book)",
                url: "https://nostarch.com/hardwarehackerpaperback"
            },
            {
                title: "Logic Analyzer Basics",
                url: "https://learn.sparkfun.com/tutorials/logic-analyzer-guide/all"
            }
        ]
    },
    {
        title: "9. Obfuscation and Anti-Reverse Engineering Techniques",
        level: "advanced",
        type: "software",
        description: "Understand and overcome techniques designed to prevent reverse engineering.",
        concepts: [
            "Code obfuscation methods",
            "Anti-debugging techniques",
            "Packing and encryption of executables",
            "Virtual machine-based protections",
            "Time-based and environment-based defenses"
        ],
        exercises: [
            "Identify and bypass basic anti-debugging checks",
            "Unpack a protected executable",
            "Deobfuscate deliberately confusing code",
            "Develop a simple deobfuscation script for a specific protection"
        ],
        resources: [
            {
                title: "Practical Reverse Engineering (book)",
                url: "https://www.wiley.com/en-us/Practical+Reverse+Engineering%3A+x86%2C+x64%2C+ARM%2C+Windows+Kernel%2C+Reversing+Tools%2C+and+Obfuscation-p-9781118787311"
            },
            {
                title: "Defeating Anti-Debugging Techniques",
                url: "https://anti-debug.checkpoint.com/"
            }
        ]
    },
    {
        title: "10. Advanced Software Reverse Engineering",
        level: "advanced",
        type: "software",
        description: "Master complex software reverse engineering techniques.",
        concepts: [
            "Reverse engineering object-oriented code",
            "Recovering class hierarchies and relationships",
            "Kernel and driver analysis",
            "Just-in-time compilation analysis",
            "Scripting and automation for analysis"
        ],
        exercises: [
            "Recover class structures from a C++ binary",
            "Analyze a device driver or kernel module",
            "Develop scripts to automate repetitive analysis tasks",
            "Perform comparative analysis between different versions of software"
        ],
        resources: [
            {
                title: "IDAPython - Extending IDA Pro with Python",
                url: "https://www.hex-rays.com/products/ida/support/idapython_docs/"
            },
            {
                title: "Windows Internals (book series)",
                url: "https://docs.microsoft.com/en-us/sysinternals/learn/windows-internals"
            }
        ]
    },
    {
        title: "11. Advanced Hardware Analysis",
        level: "advanced",
        type: "hardware",
        description: "Develop expertise in advanced hardware reverse engineering techniques.",
        concepts: [
            "Side-channel analysis",
            "Hardware security modules",
            "JTAG and boundary scan techniques",
            "Chip-off analysis and ROM reading",
            "Hardware debugging interfaces"
        ],
        exercises: [
            "Access a device through JTAG interface",
            "Perform timing or power analysis on a cryptographic device",
            "Extract and analyze firmware from an embedded system",
            "Document security vulnerabilities in hardware design"
        ],
        resources: [
            {
                title: "Hardware Security: Design, Threats, and Safeguards (book)",
                url: "https://www.amazon.com/Hardware-Security-Design-Threats-Safeguards/dp/0124186912"
            },
            {
                title: "ChipWhisperer Side-Channel Analysis Tool",
                url: "https://newae.com/tools/chipwhisperer/"
            }
        ]
    },
    {
        title: "12. Malware Analysis and Reverse Engineering",
        level: "advanced",
        type: "software",
        description: "Apply reverse engineering skills to analyze malicious software.",
        concepts: [
            "Safe malware handling practices",
            "Static and dynamic malware analysis",
            "Common malware obfuscation techniques",
            "Network traffic and command & control analysis",
            "Malware family identification"
        ],
        exercises: [
            "Set up an isolated malware analysis environment",
            "Analyze and document the behavior of a sample malware",
            "Extract configuration data or command & control information",
            "Develop indicators of compromise (IOCs) for a malware sample"
        ],
        resources: [
            {
                title: "Practical Malware Analysis (book)",
                url: "https://nostarch.com/malware"
            },
            {
                title: "REMnux - Linux Toolkit for Malware Analysis",
                url: "https://remnux.org/"
            },
            {
                title: "Any.Run Interactive Malware Analysis",
                url: "https://any.run/"
            }
        ]
    },
    {
        title: "13. Firmware Analysis and Embedded Systems",
        level: "advanced",
        type: "hardware",
        description: "Specialize in extracting and analyzing firmware from embedded devices.",
        concepts: [
            "Firmware extraction methods",
            "Bootloader analysis",
            "RTOS (Real-Time Operating System) analysis",
            "IoT device security",
            "Custom protocols in embedded systems"
        ],
        exercises: [
            "Extract and analyze firmware from a consumer IoT device",
            "Identify and exploit vulnerabilities in an embedded system",
            "Modify and repackage firmware",
            "Develop a tool to analyze custom protocols"
        ],
        resources: [
            {
                title: "IoT Hackers Handbook (book)",
                url: "https://www.apress.com/gp/book/9781484242995"
            },
            {
                title: "Binwalk Firmware Analysis Tool",
                url: "https://github.com/ReFirmLabs/binwalk"
            }
        ]
    },
    {
        title: "14. Applied Reverse Engineering Projects",
        level: "advanced",
        description: "Apply your skills to comprehensive projects that mirror real-world scenarios.",
        concepts: [
            "Project planning and documentation",
            "Combining multiple reverse engineering techniques",
            "Collaborative reverse engineering",
            "Defensive strategies based on reverse engineering findings",
            "Responsible disclosure"
        ],
        exercises: [
            "Complete a CTF (Capture The Flag) reverse engineering challenge",
            "Analyze a proprietary file format and develop a parser",
            "Audit an open source application for security vulnerabilities",
            "Document and present findings in a professional format"
        ],
        resources: [
            {
                title: "Crackmes.one - Reverse Engineering Challenges",
                url: "https://crackmes.one/"
            },
            {
                title: "Flare-On Challenge - Annual Reverse Engineering Contest",
                url: "https://www.fireeye.com/blog/threat-research/2017/10/flare-on-4-challenge-solutions.html"
            }
        ]
    }
];
