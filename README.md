
# Code with Quarkus
https://github.com/users/SufardiMadoa/projects/6
## Backlog Pengerjaan
lihat track pengerjaan saya **[ListriQu]((https://github.com/users/SufardiMadoa/projects/6))**


Proyek ini dibangun menggunakan **[Quarkus](https://quarkus.io/)** – *Supersonic Subatomic Java Framework* yang cepat, ringan, dan cocok untuk Java di era cloud-native.

---

## 🚀 Menjalankan Aplikasi

### 🔧 Mode Pengembangan (Dev Mode)

Untuk menjalankan aplikasi dalam mode pengembangan (dengan live reload dan Dev UI):

```bash
./mvnw quarkus:dev
```

> Dev UI tersedia di: [http://localhost:8080/q/dev](http://localhost:8080/q/dev)

---

## 📦 Build & Jalankan

### 🔨 Build JAR Standar

```bash
./mvnw package
```

Output ada di `target/quarkus-app/`, jalankan:

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

### 🧱 Build Über-JAR

```bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

Jalankan hasilnya:

```bash
java -jar target/*-runner.jar
```

### 🐧 Build Native Executable

```bash
./mvnw package -Dnative
```

Atau dengan container (tanpa GraalVM lokal):

```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Jalankan file native-nya:

```bash
./target/code-with-quarkus-1.0.0-SNAPSHOT-runner
```

---

## 📁 Struktur Folder

```
src
├── main
│   ├── docker/                  # Konfigurasi Docker (opsional)
│   ├── java/org/listriqu/       # Paket utama aplikasi
│   │   ├── config/              # Konfigurasi global (misalnya CORS, JWT config)
│   │   ├── dto/                 # DTO (Data Transfer Object) untuk request/response body
│   │   ├── entity/              # Entitas JPA (merepresentasikan tabel database)
│   │   ├── enums/               # Enum (misal: status_enum, role_enum)
│   │   ├── exception/           # Custom Exception & Global Error Handler
│   │   ├── repository/          # Repository berbasis Panache atau JPA
│   │   ├── resource/            # REST Endpoint (Controller)
│   │   ├── response/            # Wrapper response umum (mis. ApiResponse, PageResponse)
│   │   ├── security/            # JWT Auth, filter, provider
│   │   └── service/             # Logika bisnis dan pemrosesan data
│   └── resources/
│       ├── db/                  # File SQL tambahan (opsional)
│       ├── META-INF/            # Metadata untuk JPA (persistence.xml jika digunakan)
│       ├── application.properties # Konfigurasi aplikasi (DB, JWT, dll)
│       └── import.sql           # Data seed awal yang dijalankan saat start-up
└── test/
    └── java/...                # Unit test & integration test
```

---

## 🧩 Teknologi & Extension

- ✅ **Quarkus RESTEasy Reactive** - Membuat REST API modern dan efisien
- ✅ **Hibernate ORM with Panache** - ORM dengan sintaks lebih simpel
- ✅ **Jackson** - JSON parser
- ✅ **SmallRye JWT** - Keamanan berbasis JSON Web Token
- ✅ **PostgreSQL Driver** - Koneksi ke database PostgreSQL
- ✅ **Logging JSON** - Logging format JSON

---

## 📚 Referensi Panduan Quarkus

- [Getting Started](https://quarkus.io/guides/getting-started)
- [RESTful API](https://quarkus.io/guides/rest-json)
- [Panache ORM](https://quarkus.io/guides/hibernate-orm-panache)
- [Security JWT](https://quarkus.io/guides/security-jwt)
- [Configuration Reference](https://quarkus.io/guides/config-reference)

---

## ✍️ Penulis

> Dibuat oleh **Tim Listriqu**, untuk pengembangan aplikasi backend yang cepat, modular, dan aman.
