import 'dart:convert';
import 'package:http/http.dart' as http;

class AuthService {
  static const String _baseUrl = 'http://192.168.1.10:8080';
  
  static Future<Map<String, dynamic>> login(String email, String password) async {
    final response = await http.post(
      Uri.parse('$_baseUrl/auth/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'email': email, 'password': password}),
    );

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Credenciales incorrectas');
    }
  }
  static Future<Map<String, dynamic>> register(String username, String email, String password) async {
  final response = await http.post(
    Uri.parse('$_baseUrl/auth/register'),
    headers: {'Content-Type': 'application/json'},
    body: jsonEncode({'username': username, 'email': email, 'password': password}),
  );

  if (response.statusCode == 200) {
    return jsonDecode(response.body);
  } else {
    throw Exception('Error al registrar usuario');
  }
}

static Future<Map<String, dynamic>> changePassword(String email, String newPassword) async {
  final response = await http.put(
    Uri.parse('$_baseUrl/auth/change-password'),
    headers: {'Content-Type': 'application/json'},
    body: jsonEncode({'email': email, 'newPassword': newPassword}),
  );

  if (response.statusCode == 200) {
    return jsonDecode(response.body);
  } else {
    throw Exception('Error al cambiar la contraseña');
  }
}
}