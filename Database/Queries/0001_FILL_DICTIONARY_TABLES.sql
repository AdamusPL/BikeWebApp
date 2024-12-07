USE BikeShop;
GO

INSERT INTO [BikeParameterType] VALUES('Type');
INSERT INTO [BikeParameterType] VALUES('Frame size');
INSERT INTO [BikeParameterType] VALUES('Make');

INSERT INTO [BikeParameterAttribute] VALUES('MTB', 1);
INSERT INTO [BikeParameterAttribute] VALUES('BMX', 1);
INSERT INTO [BikeParameterAttribute] VALUES('Urban', 1);
INSERT INTO [BikeParameterAttribute] VALUES('Trekking', 1);
INSERT INTO [BikeParameterAttribute] VALUES('Road', 1);
INSERT INTO [BikeParameterAttribute] VALUES('Cross', 1);

INSERT INTO [BikeParameterAttribute] VALUES('XXS', 2);
INSERT INTO [BikeParameterAttribute] VALUES('XS', 2);
INSERT INTO [BikeParameterAttribute] VALUES('S', 2);
INSERT INTO [BikeParameterAttribute] VALUES('M', 2);
INSERT INTO [BikeParameterAttribute] VALUES('L', 2);
INSERT INTO [BikeParameterAttribute] VALUES('XL', 2);
INSERT INTO [BikeParameterAttribute] VALUES('XXL', 2);

INSERT INTO [BikeParameterAttribute] VALUES('AeroBike', 3);
INSERT INTO [BikeParameterAttribute] VALUES('Phumay', 3);
INSERT INTO [BikeParameterAttribute] VALUES('LizardCycle', 3);

INSERT INTO [BikeAttribute] VALUES(1, 1);
INSERT INTO [BikeAttribute] VALUES(2, 1);
INSERT INTO [BikeAttribute] VALUES(3, 1);
INSERT INTO [BikeAttribute] VALUES(4, 1);
INSERT INTO [BikeAttribute] VALUES(5, 1);
INSERT INTO [BikeAttribute] VALUES(6, 1);
INSERT INTO [BikeAttribute] VALUES(7, 2);
INSERT INTO [BikeAttribute] VALUES(8, 2);
INSERT INTO [BikeAttribute] VALUES(9, 2);
INSERT INTO [BikeAttribute] VALUES(10, 2);
INSERT INTO [BikeAttribute] VALUES(11, 2);
INSERT INTO [BikeAttribute] VALUES(12, 2);
INSERT INTO [BikeAttribute] VALUES(13, 2);
INSERT INTO [BikeAttribute] VALUES(14, 3);
INSERT INTO [BikeAttribute] VALUES(15, 3);
INSERT INTO [BikeAttribute] VALUES(16, 3);

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

INSERT INTO [OrderStatus] VALUES('Ordered');
INSERT INTO [OrderStatus] VALUES('In-Progress');
INSERT INTO [OrderStatus] VALUES('Ready to collect');
INSERT INTO [OrderStatus] VALUES('Completed');