# Requerimientos de Seguridad - Autenticación y Sesiones

## 1. Autenticación
- Uso de JWT para autenticación stateless.
- Contraseñas almacenadas con hashing seguro (BCrypt).
- Validación de credenciales en backend.

## 2. Manejo de Sesiones
- Token con expiración definida.
- Refresh token opcional.
- Invalidación de token en logout.

## 3. Almacenamiento
- No almacenar contraseñas en texto plano.
- Protección contra ataques de fuerza bruta.
- Validación de entrada para evitar inyección.

## 4. Riesgos Identificados
- Robo de token
- Ataques de fuerza bruta
- Exposición de credenciales

## 5. Controles Propuestos
- HTTPS obligatorio
- Rate limiting en login
- Validación de JWT en cada request protegida
