USE BikeShop;
GO

SELECT * FROM [User];

SELECT * FROM [BikeParameterAttribute];

INSERT INTO [BikeParameterAttribute] VALUES('MTB');
INSERT INTO [BikeParameterAttribute] VALUES('BMX');
INSERT INTO [BikeParameterAttribute] VALUES('Urban');
INSERT INTO [BikeParameterAttribute] VALUES('Trekking');
INSERT INTO [BikeParameterAttribute] VALUES('Road');
INSERT INTO [BikeParameterAttribute] VALUES('Cross');

INSERT INTO [BikeParameterAttribute] VALUES('XXS');
INSERT INTO [BikeParameterAttribute] VALUES('XS');
INSERT INTO [BikeParameterAttribute] VALUES('S');
INSERT INTO [BikeParameterAttribute] VALUES('M');
INSERT INTO [BikeParameterAttribute] VALUES('L');
INSERT INTO [BikeParameterAttribute] VALUES('XL');
INSERT INTO [BikeParameterAttribute] VALUES('XXL');

INSERT INTO [BikeParameterAttribute] VALUES('AeroBike');
INSERT INTO [BikeParameterAttribute] VALUES('Phumay');
INSERT INTO [BikeParameterAttribute] VALUES('LizardCycle');

INSERT INTO [BikeParameterType] VALUES('Type');
INSERT INTO [BikeParameterType] VALUES('Frame size');
INSERT INTO [BikeParameterType] VALUES('Make');

INSERT INTO [Bike] (ModelName, Price, Description, ShopAssistantId) VALUES('Swift 4', 1999.00, 'Lorem ipsum', 1);
INSERT INTO [Bike_BikeParameterType] VALUES(1, 1);
INSERT INTO [Bike_BikeParameterType] VALUES(10, 1);
INSERT INTO [Bike_BikeParameterType] VALUES(14, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(1, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(10, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(14, 1);

INSERT INTO [PartAttribute] VALUES('27.5"', 1);
INSERT INTO [PartAttribute] VALUES('29"', 1);

INSERT INTO [PartAttribute] VALUES('2 rows', 2);
INSERT INTO [PartAttribute] VALUES('3 rows', 2);
INSERT INTO [PartAttribute] VALUES('2 rows', 6);
INSERT INTO [PartAttribute] VALUES('3 rows', 6);
INSERT INTO [PartAttribute] VALUES('2 rows', 8);
INSERT INTO [PartAttribute] VALUES('3 rows', 8);

INSERT INTO [PartAttribute] VALUES('6 rows', 3);
INSERT INTO [PartAttribute] VALUES('7 rows', 3);
INSERT INTO [PartAttribute] VALUES('8 rows', 3);
INSERT INTO [PartAttribute] VALUES('9 rows', 3);
INSERT INTO [PartAttribute] VALUES('10 rows', 3);
INSERT INTO [PartAttribute] VALUES('11 rows', 3);
INSERT INTO [PartAttribute] VALUES('12 rows', 3);

INSERT INTO [PartAttribute] VALUES('6 rows', 5);
INSERT INTO [PartAttribute] VALUES('7 rows', 5);
INSERT INTO [PartAttribute] VALUES('8 rows', 5);
INSERT INTO [PartAttribute] VALUES('9 rows', 5);
INSERT INTO [PartAttribute] VALUES('10 rows', 5);
INSERT INTO [PartAttribute] VALUES('11 rows', 5);
INSERT INTO [PartAttribute] VALUES('12 rows', 5);

INSERT INTO [PartAttribute] VALUES('6 rows', 7);
INSERT INTO [PartAttribute] VALUES('7 rows', 7);
INSERT INTO [PartAttribute] VALUES('8 rows', 7);
INSERT INTO [PartAttribute] VALUES('9 rows', 7);
INSERT INTO [PartAttribute] VALUES('10 rows', 7);
INSERT INTO [PartAttribute] VALUES('11 rows', 7);
INSERT INTO [PartAttribute] VALUES('12 rows', 7);

INSERT INTO [PartAttribute] VALUES('6 rows', 9);
INSERT INTO [PartAttribute] VALUES('7 rows', 9);
INSERT INTO [PartAttribute] VALUES('8 rows', 9);
INSERT INTO [PartAttribute] VALUES('9 rows', 9);
INSERT INTO [PartAttribute] VALUES('10 rows', 9);
INSERT INTO [PartAttribute] VALUES('11 rows', 9);
INSERT INTO [PartAttribute] VALUES('12 rows', 9);

INSERT INTO [PartAttribute] VALUES('V-Brake', 4);
INSERT INTO [PartAttribute] VALUES('Mechanical', 4);
INSERT INTO [PartAttribute] VALUES('Hydraulic', 4);

INSERT INTO [PartType] VALUES('Tyres');
INSERT INTO [PartType] VALUES('Front Derailleur');
INSERT INTO [PartType] VALUES('Rear Derailleur');
INSERT INTO [PartType] VALUES('Brakes');
INSERT INTO [PartType] VALUES('Cassette');
INSERT INTO [PartType] VALUES('Front Shifters');
INSERT INTO [PartType] VALUES('Rear Shifters');
INSERT INTO [PartType] VALUES('Crankset');
INSERT INTO [PartType] VALUES('Chain');

SELECT * FROM [PartType];
SELECT * FROM [PartAttribute];

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 F', 79.99, 10, 2, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 R', 79.99, 10, 3, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('PFB', 'E-500', 139.99, 2, 4, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 CS', 109.99, 5, 5, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'Bumpy', 159.99, 20, 1, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('ClunkyJ', 'a8', 159.99, 20, 9, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 C', 89.99, 40, 8, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 FS', 159.99, 20, 6, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 RS', 159.99, 20, 7, 1);

INSERT INTO [Part_PartAttribute] VALUES(4, 1);
INSERT INTO [Part_PartAttribute] VALUES(6, 2);
INSERT INTO [Part_PartAttribute] VALUES(14, 3);
INSERT INTO [Part_PartAttribute] VALUES(4, 4);
INSERT INTO [Part_PartAttribute] VALUES(2, 5);
INSERT INTO [Part_PartAttribute] VALUES(6, 6);
INSERT INTO [Part_PartAttribute] VALUES(6, 7);
INSERT INTO [Part_PartAttribute] VALUES(4, 8);
INSERT INTO [Part_PartAttribute] VALUES(6, 9);

INSERT INTO [OrderStatus] VALUES('Ordered');
INSERT INTO [OrderStatus] VALUES('In-Progress');
INSERT INTO [OrderStatus] VALUES('Ready to collect');
INSERT INTO [OrderStatus] VALUES('Completed');

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