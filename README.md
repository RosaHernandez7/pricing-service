Pricing Service

Microservicio reactivo desarrollado con Spring Boot + WebFlux que obtiene precios de mercado desde un proveedor externo y optimiza el rendimiento mediante cache asíncrono con Caffeine.
Incluye pruebas unitarias, pipeline CI y está preparado para contenedorización.

Es un microservicio reactivo que:
* Consulta precios (quotes) desde un proveedor externo
* Expone un servicio eficiente
* Evita llamadas repetidas usando cache
* Está probado, dockerizable y listo para CI/CD

Controller
   ↓
Service (QuoteService)
   ↓
Client (MarketClient → API externa)
   ↓
Cache (Caffeine)

Tecnologías usadas:
* Java 17
* Spring Boot
* Spring WebFlux (Mono)
* Spring Cache
* Caffeine Cache
* Mockito + JUnit 5
* Reactor Test (StepVerifier)
* GitHub + GitHub Actions
* Docker (preparado)
