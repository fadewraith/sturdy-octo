SELECT * FROM event;
SELECT * FROM venue;

INSERT INTO event(name, venue_id, total_capacity, left_capacity)
VALUES 
('ColdPlay', 1, 40000, 40000),
('Bruno Mars', 2, 30000, 30000);

INSERT INTO venue(name, address, total_capacity)
VALUES
('Old Trafford', 'Manchestering, UK', 80000),
('Etihad Stadium', 'Manchestering, UK', 70000);

SELECT * FROM flyway_schema_history;

INSERT INTO customer (name, email, address)
VALUES ('Leet Journey', 'info@video.com', 'Austin, TX, USA');


SELECT * FROM event;
