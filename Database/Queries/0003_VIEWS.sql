USE BikeShop;
GO

CREATE VIEW [GeneralInfoAboutBikes] AS
SELECT b.Make, b.ModelName, b.Type, b.Price, MAX(t.Size) AS TyresSize,
CAST(MIN(d.NumberOfRows) AS VARCHAR) + 'x' + CAST(MAX(d.NumberOfRows) AS VARCHAR) AS DriveTrain
FROM [Bike] b
JOIN [Part] p ON b.Id = p.BikeId
LEFT JOIN [Derailleur] d ON p.Id = d.PartId
LEFT JOIN [Tyres] t ON p.Id = t.PartId
GROUP BY b.Id, b.Make, b.ModelName, b.Type, b.Price;

DROP VIEW [GeneralInfoAboutBikes];