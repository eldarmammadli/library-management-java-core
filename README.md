Java Core Library Management System  
Author: Eldar  

Description:  
A console-based Library Management System in Java Core.  
Supports managing books and users, borrowing/returning books, and searching by ISBN, title, author, or year.  
All main features are covered with unit tests.  

Features:  
- Manage books: add, remove, search  
- Manage users: add, remove, search  
- Borrow/return books with rules:  
   - Max 3 books per user  
   - Cannot borrow already taken books  

Technical Notes:  
- Pure Java Core; Optional and Stream API are intentionally NOT used to avoid overcomplicating basic logic and to focus on algorithms  
- ArrayList for collections  
- Custom exceptions for better error handling (BookNotFoundException, UserNotFoundException, etc.)  

Project Structure:  
src/  
    main/java/com/library/  
        manager/    # Library logic  
        model/      # Book & User classes  
        exception/  # Custom exceptions  
    test/java/com/library/manager/LibraryTest.java  

How to Run:  
1. git clone git@github.com:eldarmammadli/library-management-java-core.git  
2. Import as Maven project  
3. Run Main.java for console UI  
4. Run LibraryTest.java for unit tests  

Notes:  
- Focused on core Java and algorithmic practice  
- No Optional or Stream API used to keep logic explicit and simple  
- Simple, readable, maintainable, and interview-ready  

