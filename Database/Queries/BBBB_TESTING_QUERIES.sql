USE BikeShop;
GO

-- TABLES
SELECT * FROM [User];
SELECT * FROM [UserContact];
SELECT * FROM [UserData];
SELECT * FROM [ShopAssistant];
SELECT * FROM [Client];
SELECT * FROM [Order];
SELECT * FROM [Bike];
SELECT * FROM [Part];
SELECT * FROM [Review];
SELECT * FROM [BikeIdentificationAvailable];
SELECT * FROM [BikeIdentificationReserved];
SELECT * FROM [BikeFrameSize];
SELECT * FROM [BikeType];
SELECT * FROM [PartType];
SELECT * FROM [PartAttribute];
SELECT * FROM [OrderStatus];

-- VIEWS
SELECT * FROM [GeneralInfoAboutBikes];

-- PROCEDURES
EXEC [DetailedInfoAboutBike] '1';
EXEC [FindBikeType] 'MTB';
EXEC [FindBikeMake] 'AeroBike';
EXEC [FindTyresSize] '29';
EXEC [FindGearsNumber] '3x9';
EXEC [FindInPriceRange] '1899', '1999';
EXEC [PostBikeOpinion] 1, 5, 'Amazing Bike', 1;
EXEC [PostPartOpinion] 3, 5, 'Brakes saved me from truck!', 1;
EXEC [ProfileDetails] 1;
EXEC [ClientOrderList] 1;