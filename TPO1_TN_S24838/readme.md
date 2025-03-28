# Task 1 – Recursive File Processing with Java NIO

## 📌 Task Overview

This assignment demonstrates recursive file traversal and encoding conversion using the Java NIO API.

The program:
- Recursively searches through a given directory (`~/TPO1dir`)
- Reads the content of all regular files (assumed encoded in `Cp1250`)
- Converts their content to `UTF-8`
- Appends the converted content to a single result file: `TPO1res.txt`

---

## 📁 Project Structure
Zadanie1/ 
├── Main.java # Entry point – sets input/output paths 
└── Futil.java # Core logic – handles directory traversal and file processing

---

## 🔧 Technologies Used

- Java NIO (`FileChannel`, `Files.walkFileTree`, `ByteBuffer`)
- Charset conversion (`Cp1250` ➜ `UTF-8`)
- Exception handling and resource management using try-with-resources

---

## ⚙️ Functionality

- **Input Directory**:  
  `~/TPO1dir` (user’s home directory)  
  > Must exist and contain text files encoded in **CP1250**.

- **Output File**:  
  `TPO1res.txt`  
  > Created in the project root. If it already exists, it will be deleted and recreated.

- **Supported Files**:  
  All **regular files** (non-directories, non-symbolic links) found during traversal.

##📌 Notes

Uses 'Charset.forName("Cp1250")' for decoding input files.
Uses 'Charset.forName("UTF-8")' for encoding the output.
Traversal is recursive and includes files in subdirectories.
Skips non-regular files silently.

