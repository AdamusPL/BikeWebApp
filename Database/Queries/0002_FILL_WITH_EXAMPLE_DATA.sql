USE BikeShop;
GO

INSERT INTO [UserData] VALUES('Admin', 'Admin');
INSERT INTO [UserEmail] VALUES('admin@admin.pl', 1);
INSERT INTO [UserPhoneNumber] VALUES('+48123456789', 1);
INSERT INTO [User] VALUES('admin', 'admin', 1);

INSERT INTO [ShopAssistant] VALUES(1);

INSERT INTO [UserData] VALUES('Client', 'Example');
INSERT INTO [UserEmail] VALUES('client@test.pl', 2);
INSERT INTO [UserPhoneNumber] VALUES('+48123456788', 2);
INSERT INTO [User] VALUES('client', 'example', 2);

INSERT INTO [Client] VALUES(2);

INSERT INTO [BikeType] VALUES('MTB');
INSERT INTO [BikeType] VALUES('BMX');
INSERT INTO [BikeType] VALUES('Urban');
INSERT INTO [BikeType] VALUES('Trekking');
INSERT INTO [BikeType] VALUES('Road');
INSERT INTO [BikeType] VALUES('Cross');

INSERT INTO [BikeFrameSize] VALUES('XXS');
INSERT INTO [BikeFrameSize] VALUES('XS');
INSERT INTO [BikeFrameSize] VALUES('S');
INSERT INTO [BikeFrameSize] VALUES('M');
INSERT INTO [BikeFrameSize] VALUES('L');
INSERT INTO [BikeFrameSize] VALUES('XL');
INSERT INTO [BikeFrameSize] VALUES('XXL');

INSERT INTO [Bike] (Make, ModelName, Price, Description, BikeFrameSizeId, BikeTypeId, ShopAssistantId) VALUES('AeroBike', 'Swift 4', 1999.00, 'Lorem ipsum', 4, 1, 1);

INSERT INTO [PartType] VALUES('Tyres');
INSERT INTO [PartType] VALUES('Front Derailleur');
INSERT INTO [PartType] VALUES('Rear Derailleur');
INSERT INTO [PartType] VALUES('Brakes');
INSERT INTO [PartType] VALUES('Cassette');
INSERT INTO [PartType] VALUES('Front Shifters');
INSERT INTO [PartType] VALUES('Rear Shifters');
INSERT INTO [PartType] VALUES('Crankset');
INSERT INTO [PartType] VALUES('Chain');

INSERT INTO [PartAttribute] VALUES('27.5"', 1);
INSERT INTO [PartAttribute] VALUES('29"', 1);

INSERT INTO [PartAttribute] VALUES('2 rows', 2);
INSERT INTO [PartAttribute] VALUES('3 rows', 2);

INSERT INTO [PartAttribute] VALUES('7 rows', 3);
INSERT INTO [PartAttribute] VALUES('8 rows', 3);
INSERT INTO [PartAttribute] VALUES('9 rows', 3);
INSERT INTO [PartAttribute] VALUES('10 rows', 3);
INSERT INTO [PartAttribute] VALUES('11 rows', 3);

INSERT INTO [PartAttribute] VALUES('V-Brake', 4);
INSERT INTO [PartAttribute] VALUES('Mechanical', 4);
INSERT INTO [PartAttribute] VALUES('Hydraulic', 4);

INSERT INTO [PartAttribute] VALUES('7 rows', 5);
INSERT INTO [PartAttribute] VALUES('8 rows', 5);
INSERT INTO [PartAttribute] VALUES('9 rows', 5);
INSERT INTO [PartAttribute] VALUES('10 rows', 5);
INSERT INTO [PartAttribute] VALUES('11 rows', 5);

INSERT INTO [PartAttribute] VALUES('2 rows', 6);
INSERT INTO [PartAttribute] VALUES('3 rows', 6);

INSERT INTO [PartAttribute] VALUES('7 rows', 7);
INSERT INTO [PartAttribute] VALUES('8 rows', 7);
INSERT INTO [PartAttribute] VALUES('9 rows', 7);
INSERT INTO [PartAttribute] VALUES('10 rows', 7);
INSERT INTO [PartAttribute] VALUES('11 rows', 7);

INSERT INTO [PartAttribute] VALUES('1 row', 8);
INSERT INTO [PartAttribute] VALUES('2 rows', 8);
INSERT INTO [PartAttribute] VALUES('3 rows', 8);

INSERT INTO [PartAttribute] VALUES('7 rows', 9);
INSERT INTO [PartAttribute] VALUES('8 rows', 9);
INSERT INTO [PartAttribute] VALUES('9 rows', 9);
INSERT INTO [PartAttribute] VALUES('10 rows', 9);
INSERT INTO [PartAttribute] VALUES('11 rows', 9);

SELECT * FROM [PartAttribute];

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 F', 79.99, 10, 2, 2, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 R', 79.99, 10, 3, 1, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('PFB', 'E-500', 139.99, 2, 4, 1, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 CS', 109.99, 5, 8, 3, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'Bumpy', 159.99, 20, 1, 1, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('ClunkyJ', 'a8', 159.99, 20, 9, 1, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 C', 89.99, 40, 5, 1, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 FS', 159.99, 20, 6, 2, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartAttributeId, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 RS', 159.99, 20, 7, 1, 1);

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