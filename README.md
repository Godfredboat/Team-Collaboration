# Team-Collaboration
Expenditure Management System
Project Report: Expenditure & Receipt Module
Developer: Michael Assiama

Files Handled:
- ExpenditureManager.java
- ReceiptManager.java
- expenditures.txt
- receipts.txt

Justification of Data Structures

1. HashMap in ExpenditureManager
- Used for storing and retrieving expenditures by their unique codes.
- Why? Fast lookup, insertion, and deletion (O(1) average case).
- Functionality Supported: Retrieve by code, efficient filtering, searching, and sorting.


2. Stack in ReceiptManager
- Used to simulate the review process of uploaded receipts.
- Why? Mimics Last-In-First-Out (LIFO) behavior, ideal for accounting queue simulation.
- Functionality Supported: Upload (push), Review (pop), Persistent storage (receipts.txt).


Complexity Analysis

ExpenditureManager.java
Operation                    Method	              Time Complexity	           Explanation
Insert	                     addExpenditure()	    O(1)	                     Direct insert into HashMap
Search by Code	             getByCode()	        O(1)	                     Hash-based lookup
Search by Category/Range	   search*()	          O(n)	                     Linear scan over values
Sort by Date/Category	       sortBy*()	          O(n log n)	               Uses stream sorting with comparators

ReceiptManager.java
Operation	                   Method	              Time Complexity	           Explanation
Upload Receipt	             uploadReceipt()	    O(1)	                     Stack push
Review Receipt	             reviewNextReceipt()	O(1)	                     Stack pop
File Write	                 saveToFile()	        O(1)	                     Single-line append

Persistence
- Data is saved in plain .txt files to simulate database storage.
- Format is consistent with project requirements and easily reloadable.

Search and Sort Summary
- Search by: category, date range, amount range, bank account
- Sort by: category (A-Z), transaction date (earliest-latest)
