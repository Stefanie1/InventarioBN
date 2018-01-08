use HotelAdvisor;

INSERT INTO Hotels (name, city, state, streetAddress, lat, lon) values('Hilton San Francisco Union Square','San Francisco','CA', '333 O\'Farrell St.', 37.78616, -122.41018);

INSERT INTO Hotels (name, city, state, streetAddress, lat, lon) values('Parc 55 San Francisco - A Hilton Hotel','San Francisco','CA', '55 Cyril Magnin St', 37.784580, -122.408540);

INSERT INTO Hotels (name, city, state, streetAddress, lat, lon) values('Travelodge San Francisco Airport-North','South San Francisco','CA', '326 S Airport Blvd', 37.645129, -122.404920);







INSERT INTO users (username, password, usersalt) values ('Jovani', 'Jovani   | 97208A4C73D3494137C7D3D4E46CA9D6F1EF4EF2F1B4961746D0997838BF6F68', 'C45C8E87E59D45E014C936DF19AEC4E8');






INSERT INTO Reviews (hotelId, rating, title, review, isRecom, date, userId) 
values (3, 3.5, 'Convenient location but room too small', 'Great location, clean room, elevator moves fast. But room is a little too small. ', 1, '2016-08-19T14:28:52Z', 1);


INSERT INTO Reviews (hotelId, rating, title, review, isRecom, date, userId) 
values (3, 2, 'False Promises', 'Nothing promised in the Expedia description or their online presence was true.  No restaurant (under renovation) nor room service NOT EVEN A BOTTLE OF WATER IN THE ROOM, OR MADE AVAILABLE!!  The promise was a pass to the Kabuki Spa. NOT.  Parking $25 a night--its was $45.  The front desk actually laughed a t me when I asked a about these things.  Total disappointment for the price--320 a night.  I went to the far better property Hotel Vitale in the sameJoie de Vivre group, not through Expedia and it was much cheaper, (mid 200\'s)  totally gorgeous, and utterly professional with restaurant, room service, expresso in room, views. The Kabuki has gone way way downhill.', 0, '2016-09-14T17:23:40Z', 1);

INSERT INTO Reviews (hotelId, rating, title, review, isRecom, date, userId) 
values (3, 4.5, 'Shabby room with poor bathing facilities', 'The room furniture and fixture are shabby and dusty. Most terrible is the on-wall shower, water leaked out and streams from shower head ran to uncontrolled direction!', 1, '2016-09-11T9:30:28Z', 1);

INSERT INTO Reviews (hotelId, rating, title, review, isRecom, date, userId) 
values (3, 3.0, 'Once fine hotel showing its age', 'When we arrived in our room, it was hot and stuffy. The AC didn\'t work because of building\'s age. We opened sliding glass door and hotel staff brought a fan. Problem solved.', 1, '2016-08-19T14:28:52Z', 1);

INSERT INTO Reviews (hotelId, rating, title, review, isRecom, date, userId) 
values (3, 3.5, 'Great hotel', 'We needed a place near the Marina for a very early boat trip', 1, '2016-08-19 14:28:52', 1);



INSERT INTO Reviews (hotelId, rating, title, review, isRecom, date, userId) values (
3, 2, 'False Promises', 'Nothing promised in the Expedia description or their online presence was true.  No restaurant',0, '2016-09-14T17:23:40Z', 
(SELECT userid
         FROM users
        WHERE username = 'user'));




select h.*, AVG(r.rating) from Hotels h NATURAL JOIN Reviews r GROUP BY h.hotelId;
select h.*, AVG(r.rating) as rating from Hotels h NATURAL JOIN Reviews r where h.hotelId = 1
