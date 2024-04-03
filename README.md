Bu uygulama Java ve Spring Boot kullanılarak aşağıdaki kapsamda geliştirilmiştir.

src
├── main
│   └── java
│       └── com
│           └── bank
│               ├── entities
│               │   ├── Account.java
│               │   ├── AccountOwner.java
│               │   ├── Transaction.java
│               │   └── TransactionType.java
│               ├── service
│               │   └── BankService.java
│               │   └── AccountService.java
│               ├── repository
│               │   └── AccountRepository.java
│               │   └── AccountOwnerRepository.java
│               │   └── BankRepository.java
│               │   └── TransactionRepository.java
│               └── controller
│                   └── AccountController.java
│                   └── BankController.java
│                   └── OwnerController.java
│                   └── TransactionController.java
└── test
    └── java
        └── com
            └── bank
                └── service
                    └── AccountOwnerServiceIntegrationTest.java
                    └── AccountServiceIntegrationTest.java
                    └── BankServiceIntegrationTest.java
                    └── TransactionServiceIntegrationTest.java
                    

Uygulamada olan fonksiyonlar
• Kullanıcı yaratma
• Hesap yaratma
• Hesaba para yatırma
• Hesaptan para çekme
• Hesap bakiyesi sorgulama
• İşlem tarihçesi sorgulama

Uygulamayı çalıştırmak için ilk yapmanız gereken şey postgreSql ayarlarını güncellemek olmalı. 
Burda Db adı Kullanıcı adı ve şifresini güncelleyebilirsiniz.

src
├── main
│   └── resources
│       └── application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=1234

Daha Sonra PostgreSql altına tabloları oluşturup projeyi çalıştrıabilirsiniz. Postmen kullanarak tüm servislerin testini yapabilirsiniz.
