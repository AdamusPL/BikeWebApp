USE BikeShop;
GO

CREATE PROCEDURE [AddBike] (@Model VARCHAR(23), @ModelName VARCHAR(50), @Price DECIMAL(7,2), @QuantityInStock INT, @Description VARCHAR(500)) AS


CREATE PROCEDURE [DetailedInfoAboutBike] (@BikeId INT) AS
SELECT b.Make, b.ModelName, b.Type, b.Price, b.QuantityInStock, FrameSize,
(SELECT * FROM Part 
WHERE BikeId = @BikeId)
FROM [Bike] b
JOIN [Part] p ON b.Id = p.BikeId
WHERE b.Id = @BikeId
GROUP BY b.Id, b.Make, b.ModelName, b.Type, b.Price, b.QuantityInStock, FrameSize
GO;

CREATE PROCEDURE [RemoveBike] (@BikeId INT) AS
DELETE FROM [Bike]
WHERE [Bike].Id = @BikeId

GO;

CREATE PROCEDURE [RemovePart] (@PartId INT) AS
DELETE FROM [Part]
WHERE [Part].Id = @PartId

GO;

CREATE PROCEDURE [FindBikeType] (@BikeType VARCHAR(13)) AS
SELECT * FROM [GeneralInfoAboutBikes]
WHERE Type = @BikeType

GO;

CREATE PROCEDURE [FindBikeMake] (@BikeMake VARCHAR(23)) AS
SELECT * FROM [GeneralInfoAboutBikes]
WHERE Make = @BikeMake

GO;

CREATE PROCEDURE [FindTyresSize] (@TyreSize INT) AS
SELECT * FROM [GeneralInfoAboutBikes]
WHERE TyresSize = @TyreSize

GO;

CREATE PROCEDURE [FindGearsNumber] (@DriveTrain VARCHAR(4)) AS
SELECT * FROM [GeneralInfoAboutBikes]
WHERE DriveTrain = @DriveTrain

GO;

CREATE PROCEDURE [FindInPriceRange] (@DownRange INT, @UpRange INT) AS
SELECT * FROM [GeneralInfoAboutBikes]
WHERE Price >= @DownRange AND Price <= @UpRange

GO;

CREATE PROCEDURE [PostBikeOpinion] (@BikeId INT, @NumberOfStars INT, @Description VARCHAR(500), @ClientId INT) AS
INSERT INTO [Review](NumberOfStars, Description, BikeId, ClientId) VALUES (@NumberOfStars, @Description, @BikeId, @ClientId);

GO;

CREATE PROCEDURE [PostPartOpinion] (@PartId INT, @NumberOfStars INT, @Description VARCHAR(500), @ClientId INT) AS
INSERT INTO [Review](NumberOfStars, Description, PartId, ClientId) VALUES (@NumberOfStars, @Description, @PartId, @ClientId);

GO;

CREATE PROCEDURE [ProfileDetails] (@UserId INT) AS
SELECT Username, FirstName, LastName, PhoneNumber, Email FROM [User] u
INNER JOIN [Data] d ON u.Id = d.UserId
INNER JOIN [Contact] c ON u.Id = c.UserId
WHERE u.Id = @UserId

GO;

CREATE PROCEDURE [ClientOrderList] (@ClientId INT) AS
SELECT OrderDate, Status, b.Make, b.ModelName, p.Make, p.ModelName FROM [Order] o
INNER JOIN [Bike] b ON o.Id = b.OrderId
INNER JOIN [Part] p ON o.Id = p.OrderId
INNER JOIN [Client] c ON b.ClientId = c.Id AND p.ClientId = c.Id
WHERE c.Id = @ClientId

GO;