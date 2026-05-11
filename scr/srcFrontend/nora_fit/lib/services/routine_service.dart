import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:nora_fit/services/session.dart';

class RoutineService {
  static const String _baseUrl = 'http://192.168.1.10:8080';

  static Future<List<Map<String, dynamic>>> getRoutines() async {
    final response = await http.get(
      Uri.parse('$_baseUrl/routines?userId=${Session.userId}'),
      headers: {'Content-Type': 'application/json'},
    );
    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.cast<Map<String, dynamic>>();
    } else {
      throw Exception('Error al cargar rutinas');
    }
  }

  static Future<Map<String, dynamic>> createRoutine(String name) async {
    final response = await http.post(
      Uri.parse('$_baseUrl/routines?userId=${Session.userId}'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'name': name}),
    );
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Error al crear rutina');
    }
  }

  static Future<void> deleteRoutine(int id) async {
    final response = await http.delete(
      Uri.parse('$_baseUrl/routines/$id?userId=${Session.userId}'),
      headers: {'Content-Type': 'application/json'},
    );
    if (response.statusCode != 200) {
      throw Exception('Error al eliminar rutina');
    }
  }

  static Future<Map<String, dynamic>> renameRoutine(int id, String newName) async {
    final response = await http.put(
      Uri.parse('$_baseUrl/routines/$id/rename?userId=${Session.userId}'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'newName': newName}),
    );
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Error al renombrar rutina');
    }
  }
}