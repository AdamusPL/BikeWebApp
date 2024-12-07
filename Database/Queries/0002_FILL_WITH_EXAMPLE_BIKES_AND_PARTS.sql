USE BikeShop;
GO

INSERT INTO [Bike] (ModelName, Price, Description, ShopAssistantId) VALUES('Swift 4', 1999.00, 'Lorem ipsum', 1);
INSERT INTO [Bike_BikeParameterType] VALUES(1, 1);
INSERT INTO [Bike_BikeParameterType] VALUES(10, 1);
INSERT INTO [Bike_BikeParameterType] VALUES(14, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(1, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(10, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(14, 1);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 F', 79.99, 10, 2, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 R', 79.99, 10, 3, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('PFB', 'E-500', 139.99, 2, 4, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 CS', 109.99, 5, 5, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'Bumpy', 159.99, 20, 1, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('ClunkyJ', 'a8', 159.99, 20, 9, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 C', 89.99, 40, 8, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 FS', 159.99, 20, 6, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 RS', 159.99, 20, 7, 1);

INSERT INTO [Bike_Part] VALUES(1,1);
INSERT INTO [Bike_Part] VALUES(2,1);
INSERT INTO [Bike_Part] VALUES(3,1);
INSERT INTO [Bike_Part] VALUES(4,1);
INSERT INTO [Bike_Part] VALUES(5,1);
INSERT INTO [Bike_Part] VALUES(6,1);
INSERT INTO [Bike_Part] VALUES(7,1);
INSERT INTO [Bike_Part] VALUES(8,1);
INSERT INTO [Bike_Part] VALUES(9,1);

INSERT INTO [BikeIdentificationAvailable] VALUES('1111111111', 1);
INSERT INTO [BikeIdentificationAvailable] VALUES('1111111112', 1);
INSERT INTO [BikeIdentificationAvailable] VALUES('1111111113', 1);