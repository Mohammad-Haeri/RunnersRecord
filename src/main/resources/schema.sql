CREATE TABLE IF NOT EXISTS Run(
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    meters INT NOT NULL,
    score INT NOT NULL,
    location varchar(10) NOT NULL,
    PRIMARY KEY(id)
);