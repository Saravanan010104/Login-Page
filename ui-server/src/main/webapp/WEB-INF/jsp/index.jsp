<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${title}</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">
  <style>
    :root { --accent: #2563eb; --bg:#0b0f19; --fg:#111827; --muted:#6b7280; }
    *{box-sizing:border-box} body{font-family:Inter,system-ui,-apple-system,Segoe UI,Roboto,Helvetica,Arial,sans-serif;margin:0}
    header{position:sticky;top:0;background:white;border-bottom:1px solid #e5e7eb;padding:12px 16px;display:flex;gap:12px;align-items:center}
    input[type=search]{flex:1;padding:10px 12px;border:1px solid #e5e7eb;border-radius:8px}
    .grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(220px,1fr));gap:16px;padding:16px}
    .card{background:white;border:1px solid #e5e7eb;border-radius:12px;overflow:hidden;display:flex;flex-direction:column}
    .card img{width:100%;height:160px;object-fit:cover}
    .card .content{padding:12px}
    .price{color:var(--accent);font-weight:600}
    .btn{background:var(--accent);color:white;border:none;border-radius:8px;padding:10px 12px;cursor:pointer}
    @media(min-width:768px){.grid{gap:20px}}
  </style>
</head>
<body>
<header>
  <strong>Shop</strong>
  <input id="search" type="search" placeholder="Search products..." aria-label="Search products">
</header>
<main>
  <section class="grid" id="products" aria-live="polite"></section>
</main>
<script>
async function loadProducts() {
  const res = await fetch('/catalog/products?page=1&size=12');
  const items = await res.json();
  const grid = document.getElementById('products');
  grid.innerHTML = '';
  for (const p of items) {
    const el = document.createElement('article');
    el.className = 'card';
    el.innerHTML = `
      <img src="https://picsum.photos/seed/${p.id}/400/300" alt="${p.name}">
      <div class="content">
        <div>${p.name}</div>
        <div class="price">$${p.price.toFixed(2)}</div>
        <button class="btn" aria-label="Add ${p.name} to cart" onclick="addToCart(${p.id})">Add to cart</button>
      </div>`;
    grid.appendChild(el);
  }
}
async function addToCart(productId){
  await fetch('/cart', {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify({userId:1, productId, qty:1})});
  alert('Added to cart');
}
loadProducts();
</script>
</body>
</html>
