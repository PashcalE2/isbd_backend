create index h_idx_Клиент_ид on "Клиент" using hash(ид);
create index h_idx_Клиент_пароль on "Клиент" using hash(пароль);
create index b_idx_Клиент_вход on "Клиент" using btree(ид, пароль);

create index h_idx_Консультант_ид on "Консультант" using hash(ид);
create index h_idx_Консультант_пароль on "Консультант" using hash(пароль);
create index b_idx_Консультант_вход on "Консультант" using btree(ид, пароль);

create index h_idx_Сотрудник_ид on "Сотрудник" using hash(ид);
create index h_idx_Сотрудник_пароль on "Сотрудник" using hash(пароль);
create index b_idx_Сотрудник_вход on "Сотрудник" using btree(ид, пароль);

create index h_idx_Завод_ид on "Завод" using hash(ид);
create index h_idx_Завод_пароль on "Завод" using hash(пароль);
create index b_idx_Завод_вход on "Завод" using btree(ид, пароль);

create index b_idx_Сообщение_ид_заказа on "Сообщение" using btree(ид_заказа);
