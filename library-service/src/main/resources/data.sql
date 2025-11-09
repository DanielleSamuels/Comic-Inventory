--SERIES
INSERT INTO series (series_name, volume, publisher)
VALUES
('Titans', 4, 'DC Comics')
ON CONFLICT (series_name, volume, publisher) DO NOTHING;

--CREATORS
INSERT INTO creators (name, primary_role, active)
VALUES
('John Layman', 'Writer', TRUE),
('Pete Woods', 'Artist', TRUE),
('Wes Abbott', 'Letterer', TRUE),
('Chris Rosa', 'Associate Editor', TRUE),
('Brittany Holzherr', 'Senior Editor', TRUE),
('Marie Javins', 'Editor-in-Chief', TRUE),
('Cully Hamner', 'Cover Artist', TRUE),
('Dan Mora', 'Cover Artist', TRUE),
('Tom Taylor', 'Writer', TRUE)
ON CONFLICT (name) DO NOTHING;

--COMICS
INSERT INTO comics (series_id, title, issue, release_date, cover_month, cover_year, cover_price, upc, is_variant, variant_name, variant_artist_id, is_incentive, incentive_ratio)
VALUES
--Titans Vol 4 #20
((SELECT series_id FROM series WHERE series_name='Titans' AND volume=4), NULL, 20, DATE '2025-02-19', 'APR', 2025, 3.99, '76194138022302011', FALSE, NULL, NULL, FALSE, NULL),
--Titans Vol 4 #20 Cover B
((SELECT series_id FROM series WHERE series_name='Titans' AND volume=4), NULL, 20, DATE '2025-02-19', 'APR', 2025, 4.99, '76194138022302021', TRUE, 'Cover B', (SELECT creator_id FROM creators WHERE name='Cully Hamner'), FALSE, NULL),
-- Titans Vol 4 #1 Cover E 1:25
((SELECT series_id FROM series WHERE series_name='Titans' AND volume=4), NULL, 1, DATE '2023-05-16', 'JUL', 2023, NULL, '76194138022300151', TRUE, 'Cover E', (SELECT creator_id FROM creators WHERE name='Dan Mora'), TRUE, '1:25');

--LINK CREATORS TO COMICS
INSERT INTO comic_creators (comic_id, creator_id, role_on_issue)
VALUES
--Titans Vol 4 #20
((SELECT comic_id FROM comics WHERE upc='76194138022302011'), (SELECT creator_id FROM creators WHERE name='John Layman'), 'Writer'),
((SELECT comic_id FROM comics WHERE upc='76194138022302011'), (SELECT creator_id FROM creators WHERE name='Pete Woods'), 'Artist'),
((SELECT comic_id FROM comics WHERE upc='76194138022302011'), (SELECT creator_id FROM creators WHERE name='Pete Woods'), 'Cover Artist'),
((SELECT comic_id FROM comics WHERE upc='76194138022302011'), (SELECT creator_id FROM creators WHERE name='Wes Abbott'), 'Letterer'),
((SELECT comic_id FROM comics WHERE upc='76194138022302011'), (SELECT creator_id FROM creators WHERE name='Chris Rosa'), 'Associate Editor'),
((SELECT comic_id FROM comics WHERE upc='76194138022302011'), (SELECT creator_id FROM creators WHERE name='Brittany Holzherr'), 'Senior Editor'),
((SELECT comic_id FROM comics WHERE upc='76194138022302011'), (SELECT creator_id FROM creators WHERE name='Marie Javins'), 'Editor-in-Chief'),
--Titans Vol 4 #20 Cover B
((SELECT comic_id FROM comics WHERE upc='76194138022302021'), (SELECT creator_id FROM creators WHERE name='John Layman'), 'Writer'),
((SELECT comic_id FROM comics WHERE upc='76194138022302021'), (SELECT creator_id FROM creators WHERE name='Pete Woods'), 'Artist'),
((SELECT comic_id FROM comics WHERE upc='76194138022302021'), (SELECT creator_id FROM creators WHERE name='Wes Abbott'), 'Letterer'),
((SELECT comic_id FROM comics WHERE upc='76194138022302021'), (SELECT creator_id FROM creators WHERE name='Chris Rosa'), 'Associate Editor'),
((SELECT comic_id FROM comics WHERE upc='76194138022302021'), (SELECT creator_id FROM creators WHERE name='Brittany Holzherr'), 'Senior Editor'),
((SELECT comic_id FROM comics WHERE upc='76194138022302021'), (SELECT creator_id FROM creators WHERE name='Marie Javins'), 'Editor-in-Chief'),
((SELECT comic_id FROM comics WHERE upc='76194138022302021'), (SELECT creator_id FROM creators WHERE name='Cully Hamner'), 'Cover Artist'),
-- Titans Vol 4 #1 Cover E 1:25
((SELECT comic_id FROM comics WHERE upc='76194138022300151'), (SELECT creator_id FROM creators WHERE name='Tom Taylor'), 'Writer')
ON CONFLICT (comic_id, creator_id, role_on_issue) DO NOTHING;