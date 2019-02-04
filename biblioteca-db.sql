--1. Who checked out the book 'The Hobbit’?
--Anand Beck

SELECT member.name
FROM member
WHERE member.id IN (
     SELECT checkout_item.member_id
     FROM book, checkout_item
     WHERE checkout_item.book_id =
     book.id AND book.title = 'The Hobbit'
);

-- 2. How many people have not checked out anything?
-- 37

SELECT COUNT(member.name)
FROM member
WHERE member.id NOT IN (
    SELECT member_id
    FROM checkout_item    
);

-- 3. What books and movies aren't checked out?
-- (There must be a more elegant way to achieve this?)
-- 1984
-- Catcher in the Rye
-- Crouching Tiger, Hidden Dragon
-- Domain Driven Design
-- Fellowship of the Ring
-- Lawrence of Arabia
-- Office Space
-- Thin Red Line
-- To Kill a Mockingbird
-- Tom Sawyer

SELECT book.title 
FROM book 
WHERE NOT EXISTS 
(SELECT * 
FROM checkout_item 
WHERE checkout_item.book_id = book.id)
UNION
SELECT movie.title
FROM movie
WHERE NOT EXISTS
(SELECT * 
FROM checkout_item 
WHERE checkout_item.movie_id = movie.id);

-- 4. Add the book 'The Pragmatic Programmer', and add yourself as a member. Check out 'The Pragmatic Programmer'. Use your query from question 1 to verify that you have checked it out. Also, provide the SQL used to update the database.
-- 5. Who has checked out more that 1 item? 
