--STOCK ITEMS
INSERT INTO stock_items (comic_id, num_in_stock, num_ordered, num_reserved, list_price, for_sale, is_back_issue, item_notes, created_on, last_updated)
VALUES
(1, 25, 5, 2, 3.99, TRUE, TRUE, 'NM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 0, 0, 1.00, TRUE, TRUE, 'Damaged', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--STOCK UPDATES
INSERT INTO stock_updates (stock_item_id, update_type, quantity_delta, prev_quantity, new_quantity, created_date, update_notes)
VALUES
(1, 'DAMAGE', -1, 27, 26, CURRENT_TIMESTAMP, ''),
(1, 'SALE', -1, 26, 27, CURRENT_TIMESTAMP, '');