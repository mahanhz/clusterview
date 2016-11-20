INSERT INTO ACTIVITY VALUES('sc', 'Study Circle');
INSERT INTO ACTIVITY VALUES('dm', 'Devotional Meeting');
INSERT INTO ACTIVITY VALUES('jyg', 'Junior Youth Group');
INSERT INTO ACTIVITY VALUES('cc', 'Childrens class');
INSERT INTO ACTIVITY VALUES('hv', 'Home Visit');

INSERT INTO COUNTRY VALUES('se');

INSERT INTO REGION VALUES('central', '128, 136, 25, 88', 'se');
INSERT INTO REGION VALUES('southern', '128, 136, 25, 88', 'se');

INSERT INTO CLUSTER VALUES('stockholm', '128, 136, 25, 88', 'central');
INSERT INTO CLUSTER VALUES('uppsala', '128, 136, 25, 88', 'central');
INSERT INTO CLUSTER VALUES('gotland', '128, 136, 25, 88', 'southern');

INSERT INTO TEAM VALUES(901, 127, 138, 'stockholm');
INSERT INTO TEAM VALUES(902, 58, 69, 'uppsala');

/* Hibernate generates columns in alphabetic order */
INSERT INTO MEMBER VALUES(111, 'John', 'Doe', 'M', 'I', 901);
INSERT INTO MEMBER VALUES(222, 'Jane', 'Die', 'O', 'II', 901);
INSERT INTO MEMBER VALUES(333, 'Michael', 'Rie', 'T', 'III', 902);

INSERT INTO CAPABILITY VALUES(1, 'sc', 111);
INSERT INTO CAPABILITY VALUES(2, 'cc', 111);
INSERT INTO CAPABILITY VALUES(3, 'jyg', 111);
INSERT INTO CAPABILITY VALUES(4, 'dm', 222);
INSERT INTO CAPABILITY VALUES(5, 'hv', 333);
INSERT INTO CAPABILITY VALUES(6, 'jyg', 333);

INSERT INTO COMMITMENT VALUES(12, 'sc', 111);
INSERT INTO COMMITMENT VALUES(13, 'dm', 222);
INSERT INTO COMMITMENT VALUES(14, 'hv', 333);
INSERT INTO COMMITMENT VALUES(15, 'jyg', 333);

