DROP TABLE IF EXISTS image;
CREATE TABLE IF NOT EXISTS image (
                                     id SERIAL CONSTRAINT image_id PRIMARY KEY,
                                     name varchar(50) NOT NULL,
    url varchar(255) NOT NULL,
    extension varchar(60) NOT NULL
    );

DROP TABLE IF EXISTS user_profile;
CREATE TABLE IF NOT EXISTS user_profile
(
    id SERIAL CONSTRAINT user_id PRIMARY KEY,
    name varchar(50) NOT NULL,
    surname varchar(60) NOT NULL,
    age INTEGER CONSTRAINT customers_age_check CHECK(age > 0 AND age < 100),
    avatar_image_id int, foreign key (avatar_image_id) references image(id),
    password VARCHAR(255) NOT NULL
    );


DROP TABLE IF EXISTS post;
CREATE TABLE IF NOT EXISTS post (
                                    id SERIAL CONSTRAINT post_id PRIMARY KEY,
                                    text TEXT NOT NULL,
                                    user_id int, foreign key (user_id) references user_profile(id),
    date timestamp default CURRENT_TIMESTAMP
    );
DROP TABLE IF EXISTS comment;
CREATE TABLE IF NOT EXISTS comment(
                                      id SERIAL CONSTRAINT comment_id PRIMARY KEY,
                                      text TEXT NOT NULL,
                                      user_id int, foreign key (user_id) REFERENCES user_profile(id),
    date timestamp DEFAULT CURRENT_TIMESTAMP
    );

DROP TABLE IF EXISTS likes;
CREATE TABLE IF NOT EXISTS likes (
                                     id serial CONSTRAINT like_id PRIMARY KEY
);


DROP TABLE IF EXISTS post_image;
CREATE TABLE IF NOT EXISTS post_image (
                                          post_id int NOT NULL,FOREIGN KEY (post_id) REFERENCES post(id),
    image_id int NOT NULL, FOREIGN KEY (image_id) REFERENCES image(id),
    PRIMARY KEY (post_id, image_id)
    );

DROP TABLE IF EXISTS comment_image;
CREATE TABLE IF NOT EXISTS comment_image (
                                             comment_id int NOT NULL,FOREIGN KEY (comment_id) REFERENCES comment(id),
    image_id int NOT NULL, FOREIGN KEY (image_id) REFERENCES image(id),
    PRIMARY KEY (comment_id, image_id)
    );

DROP TABLE IF EXISTS post_like;
CREATE TABLE IF NOT EXISTS post_like (
                                         post_id int NOT NULL,FOREIGN KEY (post_id) REFERENCES post(id),
    like_id int NOT NULL, FOREIGN KEY (like_id) REFERENCES likes(id),
    PRIMARY KEY (post_id, like_id)
    );

DROP TABLE IF EXISTS comment_like;
CREATE TABLE IF NOT EXISTS comment_like (
                                            comment_id int NOT NULL,FOREIGN KEY (comment_id) REFERENCES comment(id),
    like_id int NOT NULL, FOREIGN KEY (like_id) REFERENCES likes(id),
    PRIMARY KEY (comment_id, like_id)
    );

DROP TABLE IF EXISTS friends;
CREATE TABLE IF NOT EXISTS friends(
                                      user_id int, foreign key(user_id) references user_profile(id),
    friend_id int, foreign key(user_id) references user_profile(id),
    PRIMARY KEY (user_id, friend_id)
    )

-- alter table likes add a data

ALTER TABLE likes
    ADD date timestamp DEFAULT CURRENT_TIMESTAMP;

-- alter add column role
ALTER TABLE user_profile ADD COLUMN role VARCHAR(20) SET DEFAULT 'USER';


-- alter add column email
ALTER TABLE user_profile ADD COLUMN email VARCHAR(50) ADD CONSTRAINT email_unique UNIQUE;


