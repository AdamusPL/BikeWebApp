USE BikeShop;
GO

CREATE TABLE [UserData](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	FirstName VARCHAR(18) NOT NULL,
	LastName VARCHAR(48) NOT NULL,
);

CREATE TABLE [User](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	Username VARCHAR(30) UNIQUE NOT NULL,
	Password VARCHAR(128) NOT NULL,
	UserDataId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(UserDataId) REFERENCES [UserData](Id)
);

CREATE TABLE [UserContact](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	PhoneNumber VARCHAR(13) UNIQUE NOT NULL,
	Email VARCHAR(64) UNIQUE NOT NULL,
	UserDataId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(UserDataId) REFERENCES [UserData](Id)
);

CREATE TABLE [ShopAssistant](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	UserDataId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(UserDataId) REFERENCES [UserData](Id)
);

CREATE TABLE [Client](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	UserDataId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(UserDataId) REFERENCES [UserData](Id)
);

CREATE TABLE [Order](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	OrderDate DATE NOT NULL,
	Status VARCHAR(11) NOT NULL,
	PRIMARY KEY(Id),
);

CREATE TABLE [Bike](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	Make VARCHAR(23) NOT NULL,
	ModelName VARCHAR(50) NOT NULL,
	Type VARCHAR(13) NOT NULL,
	Price DECIMAL(7,2) NOT NULL,
	QuantityInStock INT NOT NULL,
	FrameSize VARCHAR(4) NOT NULL,
	Description VARCHAR(500) NOT NULL,
	ShopAssistantId INT NOT NULL,
	ClientId INT,
	OrderId INT,
	PRIMARY KEY(Id),
	FOREIGN KEY(ShopAssistantId) REFERENCES [ShopAssistant](Id),
	FOREIGN KEY(ClientId) REFERENCES [Client](Id),
	FOREIGN KEY(OrderId) REFERENCES [Order](Id)
);

CREATE TABLE [Part](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	Make VARCHAR(23) NOT NULL,
	ModelName VARCHAR(50) NOT NULL,
	Price DECIMAL(7,2) NOT NULL,
	QuantityInStock INT NOT NULL,
	BikeId INT,
	ShopAssistantId INT NOT NULL,
	ClientId INT,
	OrderId INT,
	PRIMARY KEY(Id),
	FOREIGN KEY(BikeId) REFERENCES [Bike](Id),
	FOREIGN KEY(ShopAssistantId) REFERENCES [ShopAssistant](Id),
	FOREIGN KEY(ClientId) REFERENCES [Client](Id),
	FOREIGN KEY(OrderId) REFERENCES [Order](Id)
);

CREATE TABLE [Review](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	NumberOfStars INT NOT NULL,
	Description VARCHAR(500),
	BikeId INT,
	PartId INT,
	ClientId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(BikeId) REFERENCES [Bike](Id),
	FOREIGN KEY(PartId) REFERENCES [Part](Id),
	FOREIGN KEY(ClientId) REFERENCES [Client](Id)
);

CREATE TABLE [Derailleur](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	Type VARCHAR(5) NOT NULL,
	NumberOfRows INT NOT NULL,
	PartId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(PartId) REFERENCES [Part](Id)
);

CREATE TABLE [Crankset](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	NumberOfRows INT NOT NULL,
	PartId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(PartId) REFERENCES [Part](Id)
);

CREATE TABLE [Tyres](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	Size INT NOT NULL,
	PartId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(PartId) REFERENCES [Part](Id)
);

CREATE TABLE [Chain](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	NumberOfRows INT NOT NULL,
	PartId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(PartId) REFERENCES [Part](Id)
);

CREATE TABLE [Brakes](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	Type VARCHAR(10) NOT NULL,
	PartId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(PartId) REFERENCES [Part](Id)
);

CREATE TABLE [Cassette](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	NumberOfRows INT NOT NULL,
	PartId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(PartId) REFERENCES [Part](Id)
);

CREATE TABLE [Shifter](
	Id INT UNIQUE NOT NULL IDENTITY(1,1),
	Type VARCHAR(5) NOT NULL,
	NumberOfGears INT NOT NULL,
	PartId INT NOT NULL,
	PRIMARY KEY(Id),
	FOREIGN KEY(PartId) REFERENCES [Part](Id)
);


