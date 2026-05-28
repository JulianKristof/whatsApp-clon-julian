# Refactor aplicado con patrones de diseno

## Objetivo
Separar responsabilidades, reducir acoplamiento entre controladores y repositorios, y dejar una arquitectura mas mantenible sin cambiar el contrato HTTP existente.

## Patrones de diseno incorporados

### 1) Service Layer (Singleton en Spring)
Spring crea los beans de servicio como singleton por defecto.

- `AuthService`: concentra autenticacion y registro de usuario.
- `TweetService`: concentra casos de uso de tweets.

Beneficio:
- Controladores mas delgados.
- Regla de negocio centralizada.

### 2) Factory Method
Se incorporo una fabrica de roles para encapsular la traduccion de roles en texto hacia entidades persistidas.

- `RoleAssignmentFactory` (abstraccion)
- `DefaultRoleAssignmentFactory` (implementacion)

Beneficio:
- Se elimina la logica `switch` del controlador.
- Facil extensibilidad para nuevos aliases de rol.

### 3) Strategy
Se definio una estrategia para mapear entidades `Tweet` a DTOs de salida.

- `TweetMapperStrategy` (abstraccion)
- `DefaultTweetMapper` (implementacion por defecto)

Beneficio:
- Mapeo desacoplado del servicio.
- Permite introducir nuevas estrategias de serializacion sin tocar casos de uso.

### 4) Builder
Se agrego `Tweet.Builder` para construir entidades de forma explicita y legible.

Beneficio:
- Construccion mas clara de objetos.
- Evita constructores telescopicos al crecer el modelo.

## Cambios estructurales

- Controladores ahora delegan en servicios por inyeccion por constructor.
- Se mantienen los mismos endpoints:
  - `POST /api/auth/signin`
  - `POST /api/auth/signup`
  - `GET /api/tweets`
  - `POST /api/tweets`
  - `DELETE /api/tweets/{id}`

## Principios aplicados

- Single Responsibility Principle (SRP)
- Separation of Concerns
- Dependency Inversion (uso de interfaces en Factory y Strategy)

## Riesgos y compatibilidad

- Compatibilidad HTTP: mantenida.
- Cambios internos: controladores ya no contienen reglas de negocio.
- Requiere que existan roles en base de datos (`ROLE_USER`, `ROLE_ADMIN`, opcional `ROLE_MODERATOR`).

## Siguientes mejoras recomendadas

1. Agregar pruebas unitarias para `AuthService`, `TweetService` y `DefaultRoleAssignmentFactory`.
2. Agregar excepciones de dominio tipadas y un `@ControllerAdvice` global.
3. Crear DTO de entrada para crear tweet y evitar exponer entidad JPA en request body.
