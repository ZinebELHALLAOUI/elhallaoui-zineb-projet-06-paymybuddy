-- Création de la table Account
CREATE TABLE Account (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(255)
);

-- Création de la table User
CREATE TABLE User (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password TEXT,
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    id_account INTEGER,
    FOREIGN KEY (id_account) REFERENCES Account(id)
);
ALTER TABLE User
ADD CONSTRAINT unique_email UNIQUE (email);

-- Création de la table Friend
CREATE TABLE Friend (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER,
    user_friend_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (user_friend_id) REFERENCES User(id)
);

-- Création de la table Deposit
CREATE TABLE Deposit (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    account_id INTEGER,
    amount DECIMAL,
    instant TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

-- Création de la table Withdrawal
CREATE TABLE Withdrawal (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    account_id INTEGER,
    amount DECIMAL,
    instant TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

-- Création de la table Transfer
CREATE TABLE Transfer (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    account_id_sender INTEGER,
    account_id_receiver INTEGER,
    amount DECIMAL,
    commission_percentage DOUBLE,
    instant TIMESTAMP,
    FOREIGN KEY (account_id_sender) REFERENCES Account(id),
    FOREIGN KEY (account_id_receiver) REFERENCES Account(id)
);

CREATE TABLE Authority (
    email VARCHAR(255) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (email) REFERENCES User(email)
);
