-- 1. Quantos clientes temos na base?
SELECT COUNT(*) AS total_clientes
FROM customers;

-- 2. Quantos quartos temos cadastrados?
SELECT COUNT(*) AS total_quartos
FROM rooms;

-- 3. Quantas reservas em aberto o hotel possui no momento?
SELECT COUNT(*) AS total_reservas_abertas
FROM reservations
WHERE status = 'SCHEDULED';

-- 4. Quantos quartos temos vagos no momento?
SELECT COUNT(*) AS total_quartos_vagos_cancelados
FROM rooms
WHERE id NOT IN (
    SELECT room_id
    FROM reservations
    WHERE status IN ('CANCELED', 'ABSENCE')
);

-- 5. Quantos quartos temos ocupados no momento?
SELECT COUNT(*) AS total_quartos_ocupados
FROM rooms
WHERE id IN (
    SELECT room_id
    FROM reservations
    WHERE status = 'IN_USE'
);

-- 6. Quantas reservas futuras o hotel possui?
SELECT COUNT(*) AS total_reservas_futuras
FROM reservations
WHERE status = 'SCHEDULED';

-- 7. Qual o quarto mais caro do hotel?
SELECT room_number, price
FROM rooms
ORDER BY price DESC
    LIMIT 1;

-- 8. Qual o quarto com maior histórico de cancelamentos?
SELECT room_number, COUNT(*) AS total_cancelamentos
FROM reservations
WHERE status = 'CANCELED'
GROUP BY room_number
ORDER BY total_cancelamentos DESC
    LIMIT 1;

-- 9. Liste todos os quartos e a quantidade de clientes que já ocuparam cada um.
SELECT room_number, COUNT(DISTINCT customer_id) AS total_clientes_ocupacao
FROM reservations
WHERE status IN ('FINISHED', 'IN_USE')
GROUP BY room_number;

-- 10. Quais são os 3 quartos que possuem um histórico maior de ocupações?
SELECT room_number, COUNT(DISTINCT customer_id) AS total_clientes_ocupacao
FROM reservations
WHERE status IN ('FINISHED', 'IN_USE')
GROUP BY room_number
ORDER BY total_clientes_ocupacao DESC
    LIMIT 3;

-- 11. No próximo mês, o hotel fará uma promoção para os seus 10 clientes que possuírem maior histórico de reservas e você foi acionado pelo seu time para extrair esta informação do banco de dados. Quem são os 10 clientes?
SELECT customer_name, COUNT(*) AS total_reservas
FROM customers
JOIN reservations ON customers.id = reservations.customer_id
GROUP BY customer_name
ORDER BY total_reservas DESC
LIMIT 10;