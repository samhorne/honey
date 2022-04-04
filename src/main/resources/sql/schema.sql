CREATE TABLE IF NOT EXISTS bucket_list_item (
  id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  name                   VARCHAR      NOT NULL
);