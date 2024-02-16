# Question
- Sebaiknya melakukan validation di service atau di controller, karena di angular ada trend clean service kalo tidak salah, tapi clean dengan artian hanya return response


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
