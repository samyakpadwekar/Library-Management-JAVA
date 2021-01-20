CREATE TABLE users ( user_id INT not NULL,  name VARCHAR(255),  email VARCHAR(255), phone VARCHAR(255),  password VARCHAR(255), role VARCHAR(255), PRIMARY KEY ( user_id ))";
			
CREATE TABLE books ( book_id INT not NULL, name VARCHAR(255), author VARCHAR(255), subject VARCHAR(255), price DOUBLE, ISBN VARCHAR(255), PRIMARY KEY (book_id))";

CREATE TABLE copies (copy_id INT not NULL, book_id INT not NULL, rack VARCHAR(255), status VARCHAR(255), PRIMARY KEY (copy_id), FOREIGN KEY (book_id) REFERENCES books(book_id))";

CREATE TABLE issuerecord ( issue_id INT not NULL, copy_id INT not NULL, user_id INT not NULL, issue_date DATETIME, return_duedate DATETIME, return_date DATETIME, fine DOUBLE, PRIMARY KEY (issue_id), FOREIGN KEY (user_id) REFERENCES users(user_id), FOREIGN KEY (copy_id) REFERENCES copies(copy_id))";

CREATE TABLE payments ( payment_id INT, user_id INT, amount DOUBLE, type VARCHAR(20), transanction_time DATETIME, nextpayement_duedate DATETIME, PRIMARY KEY (payment_id), FOREIGN KEY (user_id) REFERENCES users(user_id))";