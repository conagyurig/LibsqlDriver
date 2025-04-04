# Turso JDBC Driver

[![License](https://img.shields.io/github/license/conagyurig/LibsqlDriver.svg)](https://opensource.org/licenses/Apache-2.0)

A modern, lightweight JDBC driver for [Turso](https://turso.tech) — built from the ground up to support **Spring Data JDBC**, with full support for **prepared statements**, **transactions**, and **batched operations**.

---

## ✨ Features

- ✅ Pure Java implementation (no native dependencies)
- 🔐 Works with **Turso-hosted** LibSQL databases over HTTPS
- 🔄 Full **transaction support** (commit, rollback, auto-commit modes)
- 🧠 Supports **prepared statements** and **parameterized batching**
- 🗂 Compatible with **Spring Data JDBC**
- 🛠 Implements key portions of the JDBC spec
- 🚀 Ready for use in web apps, microservices, and CLI tools

---

## 🧪 Requirements

- **Java 21+**
- **Maven** (for building or using from source)

---

## ⚡ Usage

JDBC URL format:
```text
jdbc:libsql://<your-turso-db>.turso.io
```

Authentication uses token-based auth (Turso tokens), provided as the password in the connection Properties:

```java
Properties props = new Properties();
props.setProperty("password", "<your-auth-token>");

Connection conn = DriverManager.getConnection(
    "jdbc:libsql://<your-db>.turso.io", props
);
```
## 🧬 Spring Data JDBC Integration
Works out-of-the-box with Spring Boot + Spring Data JDBC.

You can:

Use CrudRepository / PagingAndSortingRepository

Use @Query annotations

Use @Transactional for automatic rollback

Auto-generate schema-based inserts/updates

📎 Demo project here: Spring Boot Demo with Turso JDBC

## Example usage:
```java
public class Example {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("password", "<your-turso-token>");

        try (Connection conn = DriverManager.getConnection(
                "jdbc:libsql://your-db.turso.io", props)) {

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS test (id INTEGER, name TEXT)");
                stmt.execute("INSERT INTO test VALUES (1, 'Craig')");
                ResultSet rs = stmt.executeQuery("SELECT * FROM test");

                while (rs.next()) {
                    System.out.println(rs.getInt("id") + ": " + rs.getString("name"));
                }
            }
        }
    }
}
```
