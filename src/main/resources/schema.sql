CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role ENUM('ADMIN', 'LIBRARIAN', 'USER') NOT NULL DEFAULT 'USER',
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       is_active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE books (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       genre VARCHAR(100),
                       publication_year INT,
                       isbn VARCHAR(13) UNIQUE,
                       total_copies INT NOT NULL,
                       available_copies INT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE borrow_records (
                                id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                order_id VARCHAR(255) UNIQUE NOT NULL,
                                user_id UUID NOT NULL,
                                book_id UUID NOT NULL,
                                borrow_date DATE NOT NULL,
                                return_date DATE,
                                due_date DATE NOT NULL,
                                is_returned BOOLEAN DEFAULT FALSE,
                                overdue_fee DECIMAL(10, 2) DEFAULT 0.00,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES users(id),
                                FOREIGN KEY (book_id) REFERENCES books(id)
);


CREATE TABLE reservations (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              user_id UUID NOT NULL,
                              book_id UUID NOT NULL,
                              reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              notification_sent BOOLEAN DEFAULT FALSE,
                              FOREIGN KEY (user_id) REFERENCES users(id),
                              FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE notifications (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               user_id UUID NOT NULL,
                               book_id UUID,
                               type ENUM('OVERDUE', 'RESERVATION_AVAILABLE') NOT NULL,
                               notification_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               is_sent BOOLEAN DEFAULT FALSE,
                               FOREIGN KEY (user_id) REFERENCES users(id),
                               FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE reviews (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         user_id UUID NOT NULL,
                         book_id UUID NOT NULL,
                         rating INT CHECK (rating BETWEEN 1 AND 5),
                         review_text TEXT,
                         review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES users(id),
                         FOREIGN KEY (book_id) REFERENCES books(id)
);

-- Likes table
CREATE TABLE likes (
                       id CHAR(36) PRIMARY KEY NOT NULL,
                       book_id CHAR(36) NOT NULL,
                       user_id CHAR(36) NOT NULL,
                       liked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP,
                       FOREIGN KEY (book_id) REFERENCES videos(id) ON DELETE CASCADE,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE analytics (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           metric_name VARCHAR(100) NOT NULL,
                           metric_value VARCHAR(255) NOT NULL,
                           calculation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE otp (
                     id CHAR(36) PRIMARY KEY NOT NULL,
                     user_id CHAR(36) NOT NULL,
                     email VARCHAR(255) NOT NULL,
                     otp_code VARCHAR(50) NOT NULL,
                     otp_expiry_date TIMESTAMP NOT NULL,
                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                     updated_at TIMESTAMP,
                     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE password_reset_tokens (
                                       id CHAR(36) PRIMARY KEY NOT NULL,
                                       user_id CHAR(36) NOT NULL,
                                       token VARCHAR(255) NOT NULL,
                                       expiry_date TIMESTAMP NOT NULL,
                                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP,
                                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

