--create database multipleChoice
--drop  database multipleChoice
use multipleChoice
--use Demo
--select * from chapter where idsemester='HK01'

--drop table tblUser
CREATE TABLE tblUser (
	id char(6),
	userName varchar(140) unique not null,	
	realName nvarchar(140) not null,
	pass varchar(20) not null,
	isAdmin bit not null,
	CONSTRAINT PK_user PRIMARY KEY (id),	
	)

--drop table semester
CREATE TABLE semester (
	idsemester char(4) ,
	namesemester nvarchar(140),	
	CONSTRAINT PK_semester PRIMARY KEY (idsemester),	
	)

	--drop table chapter
	CREATE TABLE chapter (
	idchapter char(4) ,
	namechapter nvarchar(240),	
	idsemester char(4) not null,
	CONSTRAINT PK_chapter PRIMARY KEY (idchapter),	
	CONSTRAINT FK_chapter FOREIGN KEY (idsemester) REFERENCES semester(idsemester)
	)

	--bảng User
	INSERT INTO tblUser(id, username, realname, pass, isAdmin) VALUES
	('usr001', 'admin', 'ThaySon', 'admin', 1),
	('usr002', 'user2', 'User 2', 'user2', 0),
	('usr003', 'user3', 'User 3', 'user3', 0),
	('usr004', 'user4', 'User 4', 'user4', 0),
	('usr005', 'user5', 'User 5', 'user5', 0),
	('usr006', 'user6', 'User 6', 'user6', 0)



	--Bang Semester
	INSERT INTO Semester(idsemester,namesemester) VALUES ('HK01', N'Học kỳ 1')
	INSERT INTO Semester(idsemester,namesemester) VALUES ('HK02', N'Học kỳ 2')

	--bang Chapter
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH01', N'Phép cộng trong phạm vi 1.000','HK01')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH02', N'Phép trừ trong phạm vi 1.000','HK01')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH03', N'Phép nhân trong phạm vi 1.000','HK01')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH04', N'Phép chia trong phạm vi 1.000','HK01')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH05', N'Hình học + Đơn vị đo','HK01')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH06', N'Tính giá trị biểu thức trong phạm vi 1.000','HK01')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH07', N'Phép cộng trong phạm vi 10.000','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH08', N'Phép trừ trong phạm vi 10.000','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH09', N'Phép nhân trong phạm vi 10.000','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH10', N'Phép chia trong phạm vi 10.000','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH11', N'Phép cộng trong phạm vi 100.000','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH12', N'Phép trừ trong phạm vi 100.000','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH13', N'Phép nhân trong phạm vi 100.000','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH14', N'Phép chia trong phạm vi 100.000','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH15', N'Đọc số','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH16', N'Đổi đơn vị đo','HK02')
	INSERT INTO chapter(idchapter,namechapter,idsemester) VALUES ('CH17', N'Tính giá trị biểu thức phạm vi 100.000','HK02')

	--ALTER TABLE QUESTIONBANK ALTER COLUMN IDCHAPTER CHAR(4) NOT NULL
	--ALTER TABLE QUESTIONBANK ALTER COLUMN IDQUESTION CHAR(6) NOT NULL
	--ALTER TABLE QUESTIONBANK ADD CONSTRAINT PK_QUESTIONBANK PRIMARY KEY (IDQUESTION)
	--ALTER TABLE QUESTIONBANK ADD CONSTRAINT FK_QUESTIONBANK FOREIGN KEY (IDCHAPTER) REFERENCES CHAPTER(IDCHAPTER)
	--select * from questionbank

--drop table exam
CREATE TABLE exam (
	idexam INT IDENTITY(1,1) ,
	idsubexam nvarchar(140) unique,
	nameexam nvarchar(140),	
	quantity int,
	timeexam int,
	datecreate smalldatetime,
	idsemester char(4),
	CONSTRAINT PK_exem PRIMARY KEY (idexam ),
	CONSTRAINT FK_exem FOREIGN KEY (idsemester) REFERENCES semester(idsemester)
	)

	--select * from exam
	--drop table answerBank
	--select * from  questionoption where idquestion = 'add01'


	--DROP TABLE questionoption

	-- ALTER TABLE questionoption ADD id int IDENTITY(1,1)

	-- ALTER TABLE questionoption DROP COLUMN idoption

	--ALTER TABLE questionoption ALTER COLUMN idoption CHAR(7) NOT NULL
	--ALTER TABLE questionoption ALTER COLUMN IDQUESTION CHAR(6) NOT NULL
	--ALTER TABLE questionoption ADD CONSTRAINT PK_questionoption PRIMARY KEY (idoption )
	--ALTER TABLE questionoption ADD CONSTRAINT FK_questionoption FOREIGN KEY (IDQUESTION) REFERENCES QUESTIONBANK(IDQUESTION) ON DELETE CASCADE
	CREATE TABLE questionoption(
		idoption int identity(1,1) primary key,
		idquestion char(6) FOREIGN KEY REFERENCES questionbank(idquestion) not null,
		title nvarchar(200) not null,
		isCorrect bit not null
	)

	CREATe TABLE questionbank (
		idquestion char(6) PRIMARY KEY,
		ContentQuestions nvarchar(200) not null,
		[image] nvarchar(255) not null,
		idchapter char(4) FOREIGN KEY REFERENCES chapter(idchapter) not null
	)



	--create table questionbank(
	--idquestion char(6),
	--contentquestions  nvarchar(1400),	
	--images varchar(200),
	--idchapter char(4),
	--CONSTRAINT PK_questionbank PRIMARY KEY  (idquestion),
	--CONSTRAINT FK_questionbank FOREIGN KEY (idchapter) REFERENCES chapter(idchapter))

--lấy n dòng dữ liệu bắt đầu từ dòng thứ m
--	SELECT * FROM QUESTIONBANK Where idchapter='CH02' ORDER BY IDQUESTION OFFSET 1 ROWS FETCH NEXT 1 ROWS ONLY
--SELECT * FROM QUESTIONBANK Where idchapter='CH03' ORDER BY IDQUESTION OFFSET 1 ROWS FETCH NEXT 1 ROWS ONLY

--SELECT * FROM QUESTIONBANK Where idquestion='Q00002' ORDER BY IDQUESTION OFFSET 1 ROWS FETCH NEXT 1 ROWS ONLY
--SELECT * FROM answerBank Where idquestion='Q00002' ORDER BY IDQUESTION OFFSET 1 ROWS FETCH NEXT 1 ROWS ONLY

CREATE TABLE examdetail(
	idexam int FOREIGN KEY REFERENCES exam(idexam),
	idquestion char(6) FOREIGN KEY REFERENCES questionbank(idquestion),
	PRIMARY KEY(idexam, idquestion)
)

CREATE TABLE examresult (
	idexam int FOREIGN KEY REFERENCES exam(idexam),
	iduser char(6) FOREIGN KEY REFERENCES tblUser(id),
	score decimal(10,2) DEFAULT 0.00,
	primary key (idexam, iduser)
)