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
    id_account INTEGER,
    FOREIGN KEY (id_account) REFERENCES Account(id)
);

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

-- Insertion des comptes
INSERT INTO Account (account_number) VALUES ('CompteUser1'), ('CompteUser2');

-- Insertion des utilisateurs
INSERT INTO User (first_name, last_name, email, password, id_account)
VALUES
    ('John', 'Doe', 'john@example.com', 'password1', 1), -- Le compte 1 est associé à John
    ('Alice', 'Smith', 'alice@example.com', 'password2', 2); -- Le compte 2 est associé à Alice

-- Insertion des dépôts
INSERT INTO Deposit (account_id, amount, instant)
VALUES
    (1, 100.00, NOW()), -- Dépôt de 100 pour John
    (2, 150.00, NOW()); -- Dépôt de 150 pour Alice

-- Établissement de la relation d'amitié entre John et Alice
INSERT INTO Friend (user_id, user_friend_id) VALUES (1, 2); -- John est ami avec Alice
