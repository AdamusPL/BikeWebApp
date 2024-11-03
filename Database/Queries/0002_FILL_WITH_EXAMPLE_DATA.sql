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

INSERT INTO [BikeParameterType] VALUES('Type', 1);
INSERT INTO [BikeParameterType] VALUES('Type', 2);
INSERT INTO [BikeParameterType] VALUES('Type', 3);
INSERT INTO [BikeParameterType] VALUES('Type', 4);
INSERT INTO [BikeParameterType] VALUES('Type', 5);
INSERT INTO [BikeParameterType] VALUES('Type', 6);
INSERT INTO [BikeParameterType] VALUES('Frame size', 7);
INSERT INTO [BikeParameterType] VALUES('Frame size', 8);
INSERT INTO [BikeParameterType] VALUES('Frame size', 9);
INSERT INTO [BikeParameterType] VALUES('Frame size', 10);
INSERT INTO [BikeParameterType] VALUES('Frame size', 11);
INSERT INTO [BikeParameterType] VALUES('Frame size', 12);
INSERT INTO [BikeParameterType] VALUES('Frame size', 13);
INSERT INTO [BikeParameterType] VALUES('Make', 14);
INSERT INTO [BikeParameterType] VALUES('Make', 15);
INSERT INTO [BikeParameterType] VALUES('Make', 16);

INSERT INTO [Bike] (ModelName, Price, Description, ShopAssistantId) VALUES('Swift 4', 1999.00, 'Lorem ipsum', 1);
INSERT INTO [Bike_BikeParameterType] VALUES(1, 1);
INSERT INTO [Bike_BikeParameterType] VALUES(10, 1);
INSERT INTO [Bike_BikeParameterType] VALUES(14, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(1, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(10, 1);
INSERT INTO [Bike_BikeParameterAttribute] VALUES(14, 1);

INSERT INTO [PartAttribute] VALUES('27.5"');
INSERT INTO [PartAttribute] VALUES('29"');

INSERT INTO [PartAttribute] VALUES('2 rows');
INSERT INTO [PartAttribute] VALUES('3 rows');

INSERT INTO [PartAttribute] VALUES('6 rows');
INSERT INTO [PartAttribute] VALUES('7 rows');
INSERT INTO [PartAttribute] VALUES('8 rows');
INSERT INTO [PartAttribute] VALUES('9 rows');
INSERT INTO [PartAttribute] VALUES('10 rows');
INSERT INTO [PartAttribute] VALUES('11 rows');
INSERT INTO [PartAttribute] VALUES('12 rows');

INSERT INTO [PartAttribute] VALUES('V-Brake');
INSERT INTO [PartAttribute] VALUES('Mechanical');
INSERT INTO [PartAttribute] VALUES('Hydraulic');

INSERT INTO [PartType] VALUES('Tyres', 1);
INSERT INTO [PartType] VALUES('Tyres', 2);
INSERT INTO [PartType] VALUES('Front Derailleur', 3);
INSERT INTO [PartType] VALUES('Front Derailleur', 4);
INSERT INTO [PartType] VALUES('Rear Derailleur', 5);
INSERT INTO [PartType] VALUES('Rear Derailleur', 6);
INSERT INTO [PartType] VALUES('Rear Derailleur', 7);
INSERT INTO [PartType] VALUES('Rear Derailleur', 8);
INSERT INTO [PartType] VALUES('Rear Derailleur', 9);
INSERT INTO [PartType] VALUES('Rear Derailleur', 10);
INSERT INTO [PartType] VALUES('Rear Derailleur', 11);
INSERT INTO [PartType] VALUES('Brakes', 12);
INSERT INTO [PartType] VALUES('Brakes', 13);
INSERT INTO [PartType] VALUES('Brakes', 14);
INSERT INTO [PartType] VALUES('Cassette', 5);
INSERT INTO [PartType] VALUES('Cassette', 6);
INSERT INTO [PartType] VALUES('Cassette', 7);
INSERT INTO [PartType] VALUES('Cassette', 8);
INSERT INTO [PartType] VALUES('Cassette', 9);
INSERT INTO [PartType] VALUES('Cassette', 10);
INSERT INTO [PartType] VALUES('Cassette', 11);
INSERT INTO [PartType] VALUES('Front Shifters', 3);
INSERT INTO [PartType] VALUES('Front Shifters', 4);
INSERT INTO [PartType] VALUES('Rear Shifters', 5);
INSERT INTO [PartType] VALUES('Rear Shifters', 6);
INSERT INTO [PartType] VALUES('Rear Shifters', 7);
INSERT INTO [PartType] VALUES('Rear Shifters', 8);
INSERT INTO [PartType] VALUES('Rear Shifters', 9);
INSERT INTO [PartType] VALUES('Rear Shifters', 10);
INSERT INTO [PartType] VALUES('Rear Shifters', 11);
INSERT INTO [PartType] VALUES('Crankset', 3);
INSERT INTO [PartType] VALUES('Crankset', 4);
INSERT INTO [PartType] VALUES('Chain', 5);
INSERT INTO [PartType] VALUES('Chain', 6);
INSERT INTO [PartType] VALUES('Chain', 7);
INSERT INTO [PartType] VALUES('Chain', 8);
INSERT INTO [PartType] VALUES('Chain', 9);
INSERT INTO [PartType] VALUES('Chain', 10);
INSERT INTO [PartType] VALUES('Chain', 11);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 F', 79.99, 10, 4, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 R', 79.99, 10, 6, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('PFB', 'E-500', 139.99, 2, 14, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 CS', 109.99, 5, 32, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'Bumpy', 159.99, 20, 2, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('ClunkyJ', 'a8', 159.99, 20, 34, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 C', 89.99, 40, 16, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 FS', 159.99, 20, 23, 1);
INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, PartTypeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 RS', 159.99, 20, 25, 1);

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