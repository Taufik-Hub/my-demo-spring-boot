MonoDB Spring webflux Webclient Junit Lgging exception handling

GET by id   : http://localhost:8085/products/60dc79c5090f110f656c1f59
GET all     : http://localhost:8085/products

GET events  : http://localhost:8085/products/events
POST        : http://localhost:8085/products
            {
                "name": "Makhana",
                "price": 10.99
            }
PUT        : http://localhost:8085/products/60dc79c5090f110f656c1f5a
            {
                "id": "60dc79c5090f110f656c1f5a",
                "name": "Green Tea",
                "price": 15.99
            }
DELETE      : http://localhost:8085/products/60dc7ad0090f110f656c1f5b
DELETE ALL  : http://localhost:8085/products


GET: http://localhost:8080/actuator
GET: http://localhost:8080/actuator/info
GET: http://localhost:8080/actuator/health
POST: http://localhost:8085/actuator/shutdown

<plugin>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
	<!--actuator/info git commit details-->
	<executions>
		<execution>
			<goals>
				<goal>build-info</goal>
			</goals>
		</execution>
	</executions>
</plugin>
<!--actuator/info git commit details-->
<plugin>
	<groupId>pl.project13.maven</groupId>
	<artifactId>git-commit-id-plugin</artifactId>
</plugin>
