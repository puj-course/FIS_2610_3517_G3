import 'dart:convert';
import 'package:http/http.dart' as http;

class ExerciseService {
  static const String _baseUrl = 'http://172.20.10.2:8080';

  static Future<Map<String, dynamic>> addStrengthExercise(
      int routineId, String name, String description, bool hasWeight) async {
    final response = await http.post(
      Uri.parse('$_baseUrl/exercises/strength?routineId=$routineId'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'name': name,
        'description': description,
        'hasWeight': hasWeight,
      }),
    );
    if (response.statusCode == 200) return jsonDecode(response.body);
    throw Exception('Error al agregar ejercicio');
  }

  static Future<void> deleteExercise(int exerciseId, int routineId) async {
    final response = await http.delete(
      Uri.parse('$_baseUrl/exercises/$exerciseId?routineId=$routineId'),
      headers: {'Content-Type': 'application/json'},
    );
    if (response.statusCode != 200) throw Exception('Error al eliminar ejercicio');
  }

  static Future<Map<String, dynamic>> renameExercise(int exerciseId, String newName) async {
    final response = await http.put(
      Uri.parse('$_baseUrl/exercises/$exerciseId/rename'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'newName': newName}),
    );
    if (response.statusCode == 200) return jsonDecode(response.body);
    throw Exception('Error al renombrar ejercicio');
  }

  static Future<List<Map<String, dynamic>>> getSeries(int exerciseId) async {
    final response = await http.get(
      Uri.parse('$_baseUrl/exercises/$exerciseId/series'),
      headers: {'Content-Type': 'application/json'},
    );
    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.cast<Map<String, dynamic>>();
    }
    throw Exception('Error al obtener series');
  }

  static Future<Map<String, dynamic>> addSeries(
      int exerciseId, int seriesNumber, int repetitions, double weight, int restTimeSeconds) async {
    final response = await http.post(
      Uri.parse('$_baseUrl/exercises/$exerciseId/series'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'seriesNumber': seriesNumber,
        'repetitions': repetitions,
        'weight': weight,
        'restTimeSeconds': restTimeSeconds,
      }),
    );
    if (response.statusCode == 200) return jsonDecode(response.body);
    throw Exception('Error al agregar serie');
  }

  static Future<Map<String, dynamic>> updateRepetitions(int seriesId, int repetitions) async {
    final response = await http.put(
      Uri.parse('$_baseUrl/exercises/series/$seriesId/repetitions'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'repetitions': repetitions}),
    );
    if (response.statusCode == 200) return jsonDecode(response.body);
    throw Exception('Error al actualizar repeticiones');
  }

  static Future<Map<String, dynamic>> updateWeight(int seriesId, double weight) async {
    final response = await http.put(
      Uri.parse('$_baseUrl/exercises/series/$seriesId/weight'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'weight': weight}),
    );
    if (response.statusCode == 200) return jsonDecode(response.body);
    throw Exception('Error al actualizar peso');
  }
  
static Future<void> deleteSeries(int seriesId) async {
  final response = await http.delete(
    Uri.parse('$_baseUrl/exercises/series/$seriesId'),
    headers: {'Content-Type': 'application/json'},
  );
  if (response.statusCode != 200) throw Exception('Error al eliminar serie');
}
}