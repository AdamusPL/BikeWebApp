USE BikeShop;
GO

INSERT INTO [UserData] VALUES('Admin', 'Admin');
INSERT INTO [UserContact] VALUES('+48123456789', 'admin@admin.pl', 1);
INSERT INTO [User] VALUES('admin', 'admin', 1);

INSERT INTO [ShopAssistant] VALUES(1);

INSERT INTO [UserData] VALUES('Client', 'Example');
INSERT INTO [UserContact] VALUES('+48123456788', 'client@gmail.com', 2);
INSERT INTO [User] VALUES('client', 'example', 2);

INSERT INTO [Client] VALUES(2);

INSERT INTO [Bike] (Make, ModelName, Type, Price, QuantityInStock, FrameSize, Description, ShopAssistantId) VALUES('AeroBike', 'Swift 4', 'MTB', 1999.00, 1, 'S', 'Lorem ipsum', 1);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 F', 79.99, 10, 1, 1);
INSERT INTO [Derailleur] VALUES ('Front', 3, 1);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 R', 169.99, 5, 1, 1);
INSERT INTO [Derailleur] VALUES ('Rear', 9, 2);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('PFB', 'E-500', 139.99, 2, 1, 1);
INSERT INTO [Brakes] VALUES ('Hydraulic', 3);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 CS', 109.99, 5, 1, 1);
INSERT INTO [Crankset] VALUES (3, 4);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('SwiftZPart', 'Bumpy', 159.99, 20, 1, 1);
INSERT INTO [Tyres] VALUES (29, 5);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('ClunkyJ', 'a8', 159.99, 20, 1, 1);
INSERT INTO [Chain] VALUES (9, 6);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 C', 89.99, 40, 1, 1);
INSERT INTO [Cassette] VALUES (9, 7);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 FS', 159.99, 20, 1, 1);
INSERT INTO [Shifter] VALUES ('Front', 3, 8);

INSERT INTO [Part] (Make, ModelName, Price, QuantityInStock, BikeId, ShopAssistantId) VALUES('SwiftZPart', 'E-500 RS', 159.99, 20, 1, 1);
INSERT INTO [Shifter] VALUES ('Rear', 9, 9);

INSERT INTO [Order] VALUES ('2024-08-22', 'Ordered');

