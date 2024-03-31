create type Доступные_организации_enum as enum (
	'СтройМастер',
	'GreenTech Solutions',
	'Олимпийский Мебельный Комбинат',
	'Creative Minds Co.',
	'Центр Энергосбережения',
	'Bright Future Inc.',
	'Мастерская Игрушек',
	'Smart Home Systems',
	'Здоровый Образ Жизни',
	'BlueSky Consulting',
	'Медицинский Центр "Здоровье"',
	'Sunrise Productions',
	'ТехноПро',
	'Fashion Forward Co.',
	'Горячие Пироги',
	'TechGenius Solutions',
	'ДомиКафе',
	'Crystal Clear Consulting',
	'Автомастерская "Мотор"',
	'Golden Gate Events',
	'Новые Горизонты',
	'Harmony Wellness Center',
	'Арт-Галерея "Креатив"',
	'Silver Haze Co.',
	'Эко-Строй',
	'Sparkle & Shine Cleaning Services',
	'Пиццерия "Итальяно"',
	'True North Travel Agency',
	'Ремонтная Мастерская "Ключ"',
	'Urban Oasis Co.',
	'Мода и Стиль',
	'BrightStar Solutions',
	'Sweet Dreams Bakery',
	'AquaLuxe Pools',
	'Профмаркет',
	'Zenith Consulting Group',
	'Чайная Лавка',
	'Seaside Retreat Co.',
	'Fresh Start Fitness',
	'TechWise Solutions',
	'Мода и Тренды',
	'Rainbow Dreams Daycare',
	'Ремонтные Работы "МастерШтук"',
	'Sweets & Treats Co.',
	'EcoVista Solutions',
	'ТехноПлюс',
	'BlueWave Events',
	'Home Sweet Home Realty',
	'Artisan Craft Co.',
	'Натуральная Красота',
	'UrbanEdge Design Studio',
	'Healthy Habits Co.',
	'Modern Living Solutions',
	'GreenField Farms',
	'Beauty Bliss Salon',
	'ГастроКафе "Сытый Медведь"',
	'Elite Fitness Club',
	'DreamGate Real Estate',
	'Sparkling Star Events',
	'Дизайнерская Мастерская "Креатив"',
	'FreshStart Wellness Center',
	'FlightZone Travel Agency',
	'Starlight Productions',
	'HealthyBites Cafe',
	'Ресторан "Семейная Кухня"',
	'ZenZone Meditation Center',
	'Coastal Retreat Co.',
	'ColorSplash Art Studio',
	'Solaris Energy Solutions',
	'TechSavvy Solutions',
	'Fashionista Boutique',
	'GardenGlow Landscaping',
	'WellnessWave Center',
	'Инновационные Технологии',
	'Heavenly Delights Bakery',
	'EcoFriendly Co.',
	'DreamCatcher Events',
	'Zenith Fitness Center',
	'Serenity Spa Retreat',
	'FreshEssence Co.',
	'FoodieFusion Restaurant',
	'BrightStar Marketing',
	'PurePamper Salon',
	'Crestview Consulting Group',
	'Harmony Haven Wellness Center',
	'FreshMarket Grocery Store',
	'ModernEdge Design Studio',
	'Sunbeam Energy Solutions',
	'EliteElegance Events',
	'Домашние Украшения "DeLuxe"',
	'GreenGrove Landscaping',
	'HealthfulHabits Co.',
	'UrbanChic Boutique',
	'BlueRidge Realty',
	'Harmony Home Solutions',
	'FreshStart Fitness Studio',
	'SunnySide Bakery',
	'TechZone Innovations',
	'DreamCuisine Catering'
);

create type Статус_заказа_enum as enum (
    'формируется',
    'ожидает оплаты',
    'выполняется',
    'выполнен',
    'отклонен'
);

create type Статус_продукции_в_заказе_enum as enum (
    'ожидает производства',
    'ожидает сборки',
    'ожидает возвращения',
    'возвращена',
    'собрана под заказ'
);

create type Отправитель_enum as enum (
    'клиент',
	'консультант'
);

create type Должность_enum as enum (
    'механик',
    'электрик',
    'технолог',
    'руководитель'
);

create type Состояние_оборудования_enum as enum (
    'исправно',
	'неисправно'
);

create type Форма_сотрудничества_enum as enum (
    'благотворительность',
    'партнерство'
);

create type Состояние_сотрудничества_enum as enum (
    'на рассмотрении',
    'в силе',
    'прекращено',
    'отклонено'
);

-- Более клиент-ориентированная часть

create table Служба_поддержки (
    ид serial primary key,
    название text not null,
    номер_телефона varchar(20) not null,
	email varchar(64) not null
);

create table Адрес_службы_поддержки (
	ид_службы_поддержки int primary key references Служба_поддержки(ид),
	адрес text unique not null
);

create table Расписание_консультантов (
    ид serial primary key,
	рабочее_время int not null check (рабочее_время >= 0),
	описание text
);

create table Консультант (
    ид serial primary key,
	ид_службы_поддержки int not null references Служба_поддержки(ид),
	ид_расписания int not null references Расписание_консультантов(ид),
	ФИО varchar(64) not null,
    номер_телефона varchar(20) not null,
    email varchar(64) not null,
    пароль varchar(64) not null
);

create table Клиент (
    ид serial primary key,
	номер_телефона varchar(20) not null,
    email varchar(64) not null,
    пароль varchar(64) not null
);

create table Организация_клиента (
	ид_клиента int not null references Клиент(ид),
    primary key (ид_клиента),
	название Доступные_организации_enum unique not null
);

create table Заказ (
    ид serial primary key,
	ид_клиента int not null references Клиент(ид),
	ид_консультанта int not null references Консультант(ид),
	статус Статус_заказа_enum not null,
	поступил timestamp not null,
	завершен timestamp,
	итоговая_сумма real not null check (итоговая_сумма >= 0)
);

create table Сообщение (
    ид serial primary key,
	ид_заказа int not null references Заказ(ид),
	отправитель Отправитель_enum not null,
	текст text not null,
	дата_время timestamp not null
);

create table Тип_продукции (
    ид serial primary key,
	цена real not null check (цена > 0)
);

create table Название_продукции (
	ид_типа int primary key references Тип_продукции(ид),
	название text unique not null
);

create table Описание_продукции (
	ид_типа int primary key references Тип_продукции(ид),
	описание text unique not null
);

create table Продукция_в_заказе (
	ид_заказа int not null references Заказ(ид),
	ид_типа int not null references Тип_продукции(ид),
	primary key (ид_заказа, ид_типа),
	статус Статус_продукции_в_заказе_enum not null,
	количество int not null check (количество > 0)
);

-- Более внутренняя часть

create table Завод (
    ид serial primary key,
	название text not null,
	номер_телефона varchar(20) not null,
	email varchar(64) not null,
	пароль varchar(64) not null
);

create table Адрес_завода (
	ид_завода int primary key references Завод(ид) on delete cascade,
	адрес text unique not null
);

create table Склад_готовой_продукции (
    ид serial primary key,
	ид_завода int references Завод(ид)
);

create table Адрес_склада_готовой_продукции (
	ид_склада int primary key references Склад_готовой_продукции(ид) on delete cascade,
	адрес text unique not null
);

create table Готовая_продукция (
	ид_склада int references Склад_готовой_продукции(ид),
	ид_типа int references Тип_продукции(ид),
	primary key (ид_склада, ид_типа),
	количество int not null check (количество >= 0)
);

create table Склад_сырья (
    ид serial primary key,
	ид_завода int references Завод(ид)
);

create table Адрес_склада_сырья (
	ид_склада int primary key references Склад_сырья(ид) on delete cascade,
	адрес text unique not null
);

create table Тип_материала (
	ид serial primary key,
	цена real not null check (цена > 0)
);

create table Название_материала (
	ид_типа int primary key references Тип_материала(ид) on delete cascade,
	название text unique not null
);

create table Описание_материала (
	ид_типа int primary key references Тип_материала(ид) on delete cascade,
	описание text unique not null
);

create table Материалы (
	ид_склада int references Склад_сырья(ид),
	ид_типа int references Тип_материала(ид),
	primary key (ид_склада, ид_типа),
	количество int not null check (количество >= 0)
);

create table Организация (
    ид serial primary key,
	номер_телефона varchar(20) not null,
	email varchar(64) not null
);

create table Название_организации (
	ид_организации int primary key references Организация(ид) on delete cascade,
	название Доступные_организации_enum unique not null
);

create table Сотрудничество (
    ид_завода int references Завод(ид),
    ид_организации int references Организация(ид),
	primary key (ид_завода, ид_организации),
	форма Форма_сотрудничества_enum not null,
	состояние Состояние_сотрудничества_enum not null,
	поступило timestamp not null,
	завершено timestamp
);

create table Цех (
    ид serial primary key,
	ид_завода int not null references Завод(ид),
	название text not null
);

create table Расписание_сотрудников (
    ид serial primary key,
	рабочее_время int not null check (рабочее_время >= 0),
	описание text
);

create table Сотрудник (
    ид serial primary key,
	ид_цеха int not null references Цех(ид),
	ид_расписания int not null references Расписание_сотрудников(ид),
	ФИО varchar(64) not null,
    должность Должность_enum not null,
    номер_телефона varchar(20) not null,
    email varchar(64) not null,
    пароль varchar(64) not null
);

create table Оборудование (
    ид serial primary key,
	ид_цеха int not null references Цех(ид),
	состояние Состояние_оборудования_enum not null,
	название text not null,
	описание text,
    дата_выпуска date not null
);


