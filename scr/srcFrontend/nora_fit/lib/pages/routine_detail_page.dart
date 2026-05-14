import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:nora_fit/services/exercise_service.dart';
import 'package:nora_fit/services/routine_service.dart';

class RoutineDetailPage extends StatefulWidget {
  final Map<String, dynamic> routine;

  const RoutineDetailPage({super.key, required this.routine});

  static String routeName = 'RoutineDetailPage';
  static String routePath = '/routineDetailPage';

  @override
  State<RoutineDetailPage> createState() => _RoutineDetailPageState();
}

class _RoutineDetailPageState extends State<RoutineDetailPage> {
  List<Map<String, dynamic>> _exercises = [];
  // Cache de futures de series por exerciseId — evita reconstruir en cada frame
  final Map<int, Future<List<Map<String, dynamic>>>> _seriesFutures = {};
  bool _loading = true;

  @override
  void initState() {
    super.initState();
    _loadExercises();
  }

 Future<void> _loadExercises() async {
    print('🚀 _loadExercises llamado, id: ${widget.routine['id']}, tipo: ${widget.routine['id'].runtimeType}');
    setState(() => _loading = true);
    try {
      final idValue = (widget.routine['id'] as num).toInt();
      print('📡 Llamando getRoutineById($idValue)...');
      final routine = await RoutineService.getRoutineById(idValue);
      print('✅ Rutina recibida: $routine');
      final rawList = routine['exercises'] as List<dynamic>? ?? [];
      print('✅ Ejercicios raw: $rawList');
      final exercises = rawList.cast<Map<String, dynamic>>();

      final Map<int, Future<List<Map<String, dynamic>>>> newFutures = {};
      for (final ex in exercises) {
        final id = ex['id'] as int;
        newFutures[id] = ExerciseService.getSeries(id);
      }

      setState(() {
        _exercises = exercises;
        _seriesFutures
          ..clear()
          ..addAll(newFutures);
        _loading = false;
      });
    } catch (e, stackTrace) {
      print('❌ ERROR _loadExercises: $e');
      print('❌ StackTrace: $stackTrace');
      setState(() => _loading = false);
    }
  }

  // Recarga solo las series de un ejercicio específico
  void _refreshSeries(int exerciseId) {
    setState(() {
      _seriesFutures[exerciseId] = ExerciseService.getSeries(exerciseId);
    });
  }

  Future<void> _addExercise() async {
    final nameController = TextEditingController();
    final descController = TextEditingController();
    bool hasWeight = true;

    await showDialog(
      context: context,
      builder: (ctx) => StatefulBuilder(
        builder: (ctx, setStateDialog) => AlertDialog(
          title: Text('Nuevo Ejercicio',
              style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: nameController,
                decoration: const InputDecoration(labelText: 'Nombre'),
              ),
              TextField(
                controller: descController,
                decoration: const InputDecoration(labelText: 'Descripción'),
              ),
              Row(
                children: [
                  Checkbox(
                    value: hasWeight,
                    onChanged: (v) =>
                        setStateDialog(() => hasWeight = v ?? true),
                  ),
                  Text('Usa peso', style: GoogleFonts.montserrat()),
                ],
              ),
            ],
          ),
          actions: [
            TextButton(
                onPressed: () => Navigator.pop(ctx),
                child: const Text('Cancelar')),
            TextButton(
              onPressed: () async {
                if (nameController.text.isNotEmpty &&
                    descController.text.isNotEmpty) {
                  Navigator.pop(ctx);
                  await ExerciseService.addStrengthExercise(
                    widget.routine['id'],
                    nameController.text,
                    descController.text,
                    hasWeight,
                  );
                  await _loadExercises();
                  if (mounted) {
                    ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text('Ejercicio agregado')));
                  }
                }
              },
              child: const Text('Agregar'),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _renameExercise(int exerciseId, String currentName) async {
    final controller = TextEditingController(text: currentName);
    await showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text('Renombrar',
            style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
        content: TextField(controller: controller),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(ctx),
              child: const Text('Cancelar')),
          TextButton(
            onPressed: () async {
              if (controller.text.isNotEmpty) {
                Navigator.pop(ctx);
                await ExerciseService.renameExercise(
                    exerciseId, controller.text);
                await _loadExercises();
                if (mounted) {
                  ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(content: Text('Ejercicio renombrado')));
                }
              }
            },
            child: const Text('Guardar'),
          ),
        ],
      ),
    );
  }

  Future<void> _deleteExercise(int exerciseId) async {
    await showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text('Eliminar ejercicio',
            style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
        content: const Text('¿Seguro que deseas eliminar este ejercicio?'),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(ctx),
              child: const Text('Cancelar')),
          TextButton(
            onPressed: () async {
              Navigator.pop(ctx);
              await ExerciseService.deleteExercise(
                  exerciseId, widget.routine['id']);
              await _loadExercises();
              if (mounted) {
                ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Ejercicio eliminado')));
              }
            },
            child:
                const Text('Eliminar', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  Future<void> _addSeries(int exerciseId, int currentCount) async {
    final repsController = TextEditingController(text: '10');
    final weightController = TextEditingController(text: '0');
    final restController = TextEditingController(text: '60');

    await showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text('Agregar Serie',
            style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: repsController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Repeticiones'),
            ),
            TextField(
              controller: weightController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Peso (kg)'),
            ),
            TextField(
              controller: restController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Descanso (seg)'),
            ),
          ],
        ),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(ctx),
              child: const Text('Cancelar')),
          TextButton(
            onPressed: () async {
              Navigator.pop(ctx);
              await ExerciseService.addSeries(
                exerciseId,
                currentCount + 1,                        // seriesNumber
                int.tryParse(repsController.text) ?? 10,
                double.tryParse(weightController.text) ?? 0,
                int.tryParse(restController.text) ?? 60,
              );
              _refreshSeries(exerciseId); // solo recarga las series de este ejercicio
              if (mounted) {
                ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Serie agregada')));
              }
            },
            child: const Text('Agregar'),
          ),
        ],
      ),
    );
  }

  Future<void> _editSeries(
      Map<String, dynamic> series, int exerciseId) async {
    final repsController =
        TextEditingController(text: series['repetitions'].toString());
    final weightController =
        TextEditingController(text: series['weight'].toString());

    await showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text('Editar Serie ${series['seriesNumber']}',
            style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: repsController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Repeticiones'),
            ),
            TextField(
              controller: weightController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Peso (kg)'),
            ),
          ],
        ),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(ctx),
              child: const Text('Cancelar')),
          TextButton(
            onPressed: () async {
              Navigator.pop(ctx);
              await ExerciseService.updateRepetitions(
                  series['id'], int.tryParse(repsController.text) ?? 0);
              await ExerciseService.updateWeight(series['id'],
                  double.tryParse(weightController.text) ?? 0);
              _refreshSeries(exerciseId); // recarga solo este ejercicio
              if (mounted) {
                ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Serie actualizada')));
              }
            },
            child: const Text('Guardar'),
          ),
        ],
      ),
    );
  }

  Future<void> _deleteSeries(int seriesId, int exerciseId) async {
    await showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text('Eliminar serie',
            style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
        content: const Text('¿Seguro que deseas eliminar esta serie?'),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(ctx),
              child: const Text('Cancelar')),
          TextButton(
            onPressed: () async {
              Navigator.pop(ctx);
              await ExerciseService.deleteSeries(seriesId);
              _refreshSeries(exerciseId);
              if (mounted) {
                ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Serie eliminada')));
              }
            },
            child: const Text('Eliminar',
                style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: const Color(0xFFF5F5F5),
      body: SafeArea(
        child: Column(
          children: [
            // Header
            Container(
              margin: const EdgeInsets.fromLTRB(16, 16, 16, 8),
              padding:
                  const EdgeInsets.symmetric(horizontal: 20, vertical: 14),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(20),
                boxShadow: const [
                  BoxShadow(color: Color(0x15000000), blurRadius: 8)
                ],
              ),
              child: Row(
                children: [
                  GestureDetector(
                    onTap: () => Navigator.pop(context),
                    child: const Icon(Icons.arrow_back_ios, size: 20),
                  ),
                  const SizedBox(width: 12),
                  Expanded(
                    child: Text(
                      widget.routine['routineName'] ?? '',
                      style: GoogleFonts.montserrat(
                          fontSize: 20, fontWeight: FontWeight.bold),
                    ),
                  ),
                ],
              ),
            ),

            Padding(
              padding: const EdgeInsets.fromLTRB(24, 8, 24, 4),
              child: Align(
                alignment: Alignment.centerLeft,
                child: Text('Exercises',
                    style: GoogleFonts.montserrat(
                        fontSize: 13, color: Colors.grey)),
              ),
            ),

            Expanded(
              child: _loading
                  ? const Center(child: CircularProgressIndicator())
                  : _exercises.isEmpty
                      ? Center(
                          child: Text(
                            'No hay ejercicios.\nPresiona + para agregar uno.',
                            textAlign: TextAlign.center,
                            style: GoogleFonts.montserrat(
                                fontSize: 16, color: Colors.grey),
                          ),
                        )
                      : ListView.builder(
                          padding:
                              const EdgeInsets.symmetric(horizontal: 16),
                          itemCount: _exercises.length,
                          itemBuilder: (context, index) {
                            final exercise = _exercises[index];
                            final exerciseId = exercise['id'] as int;
                            final seriesFuture = _seriesFutures[exerciseId]!;

                            return FutureBuilder<List<Map<String, dynamic>>>(
                              future: seriesFuture,
                              builder: (context, snapshot) {
                                final seriesList = snapshot.data ?? [];
                                return Container(
                                  margin: const EdgeInsets.only(bottom: 12),
                                  decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius: BorderRadius.circular(20),
                                    boxShadow: const [
                                      BoxShadow(
                                          color: Color(0x20000000),
                                          blurRadius: 8,
                                          offset: Offset(0, 4))
                                    ],
                                  ),
                                  child: Padding(
                                    padding: const EdgeInsets.all(16),
                                    child: Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                        // Nombre + menú
                                        Row(
                                          mainAxisAlignment:
                                              MainAxisAlignment.spaceBetween,
                                          children: [
                                            Expanded(
                                              child: Text(
                                                exercise['exerciseName'] ?? '',
                                                style: GoogleFonts.montserrat(
                                                    fontSize: 15,
                                                    fontWeight: FontWeight.bold,
                                                    color: const Color(
                                                        0xFFE39501)),
                                              ),
                                            ),
                                            PopupMenuButton<String>(
                                              icon: const Icon(
                                                  Icons.more_horiz,
                                                  size: 20),
                                              onSelected: (value) {
                                                if (value == 'rename') {
                                                  _renameExercise(exerciseId,
                                                      exercise['exerciseName']);
                                                }
                                                if (value == 'delete') {
                                                  _deleteExercise(exerciseId);
                                                }
                                              },
                                              itemBuilder: (ctx) => [
                                                const PopupMenuItem(
                                                    value: 'rename',
                                                    child: Text('Renombrar')),
                                                const PopupMenuItem(
                                                    value: 'delete',
                                                    child: Text('Eliminar',
                                                        style: TextStyle(
                                                            color:
                                                                Colors.red))),
                                              ],
                                            ),
                                          ],
                                        ),

                                        const SizedBox(height: 8),

                                        // Cabecera tabla
                                        if (seriesList.isNotEmpty)
                                          Padding(
                                            padding: const EdgeInsets.only(
                                                bottom: 4),
                                            child: Row(
                                              children: [
                                                _tableHeader('SET', flex: 1),
                                                _tableHeader('KG', flex: 2),
                                                _tableHeader('REPS', flex: 2),
                                                _tableHeader('', flex: 1), // acciones
                                              ],
                                            ),
                                          ),

                                        // Filas de series
                                        ...seriesList.map((s) => Container(
                                              margin: const EdgeInsets.only(
                                                  top: 4),
                                              padding:
                                                  const EdgeInsets.symmetric(
                                                      vertical: 6,
                                                      horizontal: 4),
                                              decoration: BoxDecoration(
                                                color:
                                                    const Color(0xFFF0F0F0),
                                                borderRadius:
                                                    BorderRadius.circular(8),
                                              ),
                                              child: Row(
                                                children: [
                                                  _tableCell(
                                                      s['seriesNumber']
                                                          .toString(),
                                                      flex: 1),
                                                  _tableCell(
                                                      s['weight'].toString(),
                                                      flex: 2),
                                                  _tableCell(
                                                      s['repetitions']
                                                          .toString(),
                                                      flex: 2),
                                                  // Botones editar / eliminar serie
                                                  Expanded(
                                                    flex: 1,
                                                    child: Row(
                                                      mainAxisAlignment:
                                                          MainAxisAlignment
                                                              .end,
                                                      children: [
                                                        GestureDetector(
                                                          onTap: () =>
                                                              _editSeries(
                                                                  s,
                                                                  exerciseId),
                                                          child: const Icon(
                                                              Icons.edit,
                                                              size: 16,
                                                              color: Color(
                                                                  0xFFE39501)),
                                                        ),
                                                        const SizedBox(
                                                            width: 8),
                                                        GestureDetector(
                                                          onTap: () =>
                                                              _deleteSeries(
                                                                  s['id'],
                                                                  exerciseId),
                                                          child: const Icon(
                                                              Icons.delete_outline,
                                                              size: 16,
                                                              color:
                                                                  Colors.red),
                                                        ),
                                                      ],
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            )),

                                        // Botón + agregar serie
                                        const SizedBox(height: 8),
                                        GestureDetector(
                                          onTap: () => _addSeries(
                                              exerciseId, seriesList.length),
                                          child: Row(
                                            children: [
                                              const Icon(Icons.add_circle_outline,
                                                  size: 18,
                                                  color: Color(0xFFE39501)),
                                              const SizedBox(width: 6),
                                              Text('Agregar serie',
                                                  style: GoogleFonts.montserrat(
                                                      fontSize: 12,
                                                      color:
                                                          const Color(0xFFE39501),
                                                      fontWeight:
                                                          FontWeight.w600)),
                                            ],
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                );
                              },
                            );
                          },
                        ),
            ),

            // Botón add exercise
            Container(
              margin: const EdgeInsets.fromLTRB(16, 8, 16, 16),
              padding:
                  const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(20),
                boxShadow: const [
                  BoxShadow(color: Color(0x15000000), blurRadius: 8)
                ],
              ),
              child: GestureDetector(
                onTap: _addExercise,
                child: Container(
                  width: double.infinity,
                  height: 45,
                  decoration: const BoxDecoration(
                    gradient: LinearGradient(
                      colors: [Color(0xFFC67301), Color(0xFFE39501)],
                      stops: [0.0, 1.0],
                      begin: AlignmentDirectional(1.0, 0.0),
                      end: AlignmentDirectional(-1.0, 0),
                    ),
                    borderRadius: BorderRadius.all(Radius.circular(25)),
                    boxShadow: [
                      BoxShadow(
                          color: Color(0x33000000),
                          blurRadius: 4,
                          offset: Offset(0, 4))
                    ],
                  ),
                  child: Center(
                    child: Text('add exercise',
                        style: GoogleFonts.montserrat(
                            color: Colors.white,
                            fontWeight: FontWeight.w600,
                            fontSize: 16)),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _tableHeader(String text, {required int flex}) {
    return Expanded(
      flex: flex,
      child: Text(text,
          style: GoogleFonts.montserrat(
              fontSize: 11,
              fontWeight: FontWeight.bold,
              color: Colors.grey)),
    );
  }

  Widget _tableCell(String text, {required int flex}) {
    return Expanded(
      flex: flex,
      child: Text(text,
          style: GoogleFonts.montserrat(
              fontSize: 13, fontWeight: FontWeight.w500)),
    );
  }
}