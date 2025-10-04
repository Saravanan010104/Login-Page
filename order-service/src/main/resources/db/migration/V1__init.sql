CREATE TABLE IF NOT EXISTS orders (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  total_amount NUMERIC(12,2) NOT NULL,
  status VARCHAR(32) NOT NULL,
  payment_id BIGINT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS order_items (
  order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  product_id BIGINT NOT NULL,
  qty INT NOT NULL,
  price NUMERIC(12,2) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_orders_user ON orders(user_id);
