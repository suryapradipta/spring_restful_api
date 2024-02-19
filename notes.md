# Question
- Sebaiknya melakukan validation di service atau di controller, karena di angular ada trend clean service kalo tidak salah, tapi clean dengan artian hanya return response
- problem ternyata nama entity harus plural
- Debugingnya cukup sulit karena ga dikasi tau errornya dimana dan karena apa, ga specific
- Saya masih belum tahu, kan contact punya many to one ke user. Apakah user perlu save data contact juga (apakah kedua entity harus saling nge save) atau cukup salah satu entity saja
- ASNEWR: Ada duplikasi jadinya, jika ssave di kedua entity, hindari duplikasi 

subscribe di coponent terakhir, jangan di service
component terakhir yang subscirbe

level html async pipe

intinya componet terakhir == yang menampiklan data ke user


demo graphql how it works



ada spring security , perlu mamahami concept tambahan, setting di file 1 config , specify 1 class config detail
ada class yang handle main logic, daftar ke lifecycle

framework apapunsama conceptnya






# ASNWER
* di sisi service (not final)
* klo controller, artinya == validate input sebelum sampai service, kalo service berat:
* ada heavy process, resources akan kemakan, vladiasi sederhana taruh di controller
* service supaya tidak duplikasi validation


# Kenapa jangan gunakan entity sebagai request body
* karena di beberapa endpoint api tidak semua attributes digunakan di dalam req body
* jadi bisa gunakan DTO (data transfer object) atau model

# @Data
@Data: This annotation is used to generate boilerplate code such as getters, setters, toString(), equals(), and hashCode() methods for the fields in your class.

@Builder
@Builder: This annotation is used to generate a builder pattern for your class, allowing you to conveniently construct instances of the class with chained method calls.
Example:
MyClass obj = MyClass.builder()
.field1("value1")
.field2(123)
.build();


JpaRepository<Patient, Long> tells Spring Data JPA that the repository interface manages entities of type Patient, and those entities have primary keys of type Long.

# Kenapa validasi di level service, kenapa engga di kontrollernya langsung
karena beberapa hal kita tidak bisa validasi dari request bodynya saja
contoh validasi mau digabung dengan header dan request param 
jadi itu tidak bisa digabung di controller

jadi harus disimpan dulu di 1 object, nanti ditambahkan validasi di level service 


# Resolver
1:55:00
* setiap ada controller yang butuh data user (parameter), maka secara otomatis menggunakan UserArgumentResolver
* untuk dapetin data user, kemudian check apakah tokennya ada atau tidak 
* kemudian Resolvernya ditambahkan di WebConfiguration
