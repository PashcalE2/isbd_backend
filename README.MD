
# Сделать

В соответствии с требованиями frontend, выделяю следующие фукнциональности.

## Клиент

ЛЮБЫЕ `GET/SET` действия должны пройти проверку на доступ

### Доступ
1. Войти - проверить клиента в бд `login, password`
2. Зарегистрировать `login, email, phone, password`
3. Проверка на доступ для действия `ent.client_id == sent_client_id and verified(password)`

### Профиль
1. Получить,
2. Изменить `email, phone` по `login`

### Продукция
1. Получить все типы
2. Получить укороченный вариант `product_id, product_name, product_price` по `product_name`
3. Добавить к формирующемуся заказу (единственный) по `order_id, product_id`
4. Убрать из заказа по `order_id, product_id`

### История заказов (`статус != формируется`)
1. Получить `order_id, admin_id, status, formed_at, done_at` по `client_id`

### История конкретного заказа
1. Получить список выбранной продукции `product_id, product_name,
   product_description, product_status, product_count, product_price`
   по `order_id`
2. Получить сумму за заказ `order_price` по `order_id`
3. Отменить если не отменен и не завершен по `order_id`

### История чата
1. Получить информацию по консультанту `admin_id, admin_name, admin_contacts` по `order_id`
2. Получить все сообщения `message_id, message_text, message_from, message_sent_at` по `order_id`
3. Отправить сообщение если не завершен/отклонен `message_text, message_from, message_sent_at`

### Формирующийся заказ (корзина)
1. Получить список выбранной продукции `product_id, product_name,
   product_description, product_status, product_count, product_price`
   по `order_id`
2. Изменить количество выбранной продукции по `order_id, product_id`
3. Убрать выбранную продукцию по `order_id, product_id`
4. Получить сумму за заказ `order_price` по `order_id`
5. Оплатить по `order_id`

### Текущий чат
1. Получить информацию по консультанту `admin_id, admin_name, admin_contacts` по `order_id`
2. Получить все сообщения `message_id, message_text, message_from, message_sent_at` по `order_id`
3. Отправить сообщение `message_text, message_from, message_sent_at`

## Консультант

ЛЮБЫЕ `GET/SET` действия должны пройти проверку на доступ

### Доступ
1. Войти - проверить консультанта в бд `ид, password`
2. Проверка на доступ для действия `ent.admin_id == sent_admin_id and verified(password)`

### История заказов (`статус != формируется`)
1. Получить `order_id, client_id, client_org, status, formed_at, done_at` по `admin_id`

### История конкретного заказа
1. Получить список выбранной продукции `product_id, product_name,
   product_description, product_status, product_count, product_price`
   по `order_id`
2. Получить сумму за заказ `order_price` по `order_id`
3. Запросить сборку продукции если не формируется, не отменен и не завершен по `order_id`

### История чата
1. Получить информацию по клиенту `client_id, client_org, client_contacts` по `order_id`
2. Получить все сообщения `message_id, message_text, message_from, message_sent_at` по `order_id`
3. Отправить сообщение если не завершен/отклонен `message_text, message_from, message_sent_at`


## Производитель

ЛЮБЫЕ `GET/SET` действия должны пройти проверку на доступ

### Доступ
1. Войти - проверить factory в бд `адрес, password`
2. Проверка на доступ для действия `ent.factory_id == sent_factory_id and verified(password)`

### Типы продукции
1. Получить укороченный вариант всех типов
2. Получить укороченный вариант `product_id, product_name, product_price` по `product_name`

### Изменение конкретного типа продукции
1. Изменение параметров `product_name, product_price, product_description`
2. Удаление типа если возможно

### Новый тип продукции
1. Попытка передачи параметров для создания `product_name, product_price, product_description`

### Типы материалов
1. Получить укороченный вариант всех типов
2. Получить укороченный вариант `material_id, material_name, material_price` по `material_name`

### Изменение конкретного типа материала
1. Изменение параметров `material_name, material_price, material_description`
2. Удаление типа если возможно

### Новый тип материала
1. Попытка передачи параметров для создания `material_name, material_price, material_description`
