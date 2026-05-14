import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:nora_fit/services/routine_service.dart';
import 'package:nora_fit/services/session.dart';
import 'package:nora_fit/flutter_flow/nav/nav.dart';
import 'package:nora_fit/pages/routine_detail_page.dart';

class DashboardPage extends StatefulWidget {
  const DashboardPage({super.key});

  static String routeName = 'DashboardPage';
  static String routePath = '/dashboardPage';

  @override
  State<DashboardPage> createState() => _DashboardPageState();
}

class _DashboardPageState extends State<DashboardPage> {
  List<Map<String, dynamic>> _routines = [];
  bool _loading = true;

  @override
  void initState() {
    super.initState();
    _loadRoutines();
  }

  Future<void> _loadRoutines() async {
    try {
      final routines = await RoutineService.getRoutines();
      setState(() {
        _routines = routines;
        _loading = false;
      });
    } catch (e) {
      setState(() => _loading = false);
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: ${e.toString()}')),
      );
    }
  }

  Future<void> _createRoutine() async {
    final controller = TextEditingController();
    await showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text('New Routine',
            style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
        content: TextField(
          controller: controller,
          decoration: const InputDecoration(hintText: 'Routine Name'),
        ),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(ctx),
              child: const Text('Cancel')),
          TextButton(
            onPressed: () async {
              if (controller.text.isNotEmpty) {
                Navigator.pop(ctx);
                await RoutineService.createRoutine(controller.text);
                _loadRoutines();
              }
            },
            child: const Text('Create'),
          ),
        ],
      ),
    );
  }

  Future<void> _renameRoutine(Map<String, dynamic> routine) async {
    final controller = TextEditingController(text: routine['routineName']);
    await showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text('Rename Routine',
            style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
        content: TextField(controller: controller),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(ctx),
              child: const Text('Cancel')),
          TextButton(
            onPressed: () async {
              if (controller.text.isNotEmpty) {
                Navigator.pop(ctx);
                await RoutineService.renameRoutine(
                    routine['id'], controller.text);
                _loadRoutines();
              }
            },
            child: const Text('Save'),
          ),
        ],
      ),
    );
  }

  Future<void> _deleteRoutine(Map<String, dynamic> routine) async {
    await showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text('Delete Routine',
            style: GoogleFonts.montserrat(fontWeight: FontWeight.bold)),
        content:
            Text('Sure you want to delete "${routine['routineName']}"?'),
        actions: [
          TextButton(
              onPressed: () => Navigator.pop(ctx),
              child: const Text('Cancel')),
          TextButton(
            onPressed: () async {
              Navigator.pop(ctx);
              await RoutineService.deleteRoutine(routine['id']);
              _loadRoutines();
            },
            child: const Text('Delete',
                style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  void _logout() {
    Session.clear();
    context.pushNamed('LogInPage');
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
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  GestureDetector(
                    onTap: _createRoutine,
                    child: Container(
                      width: 44,
                      height: 44,
                      decoration: const BoxDecoration(
                        shape: BoxShape.circle,
                        gradient: LinearGradient(
                          colors: [Color(0xFF0D8FCB), Color(0xFF1CB0CC)],
                          stops: [0.0, 1.0],
                          begin: AlignmentDirectional(1.0, 0.0),
                          end: AlignmentDirectional(-1.0, 0),
                        ),
                        boxShadow: [
                          BoxShadow(
                              color: Color(0x33000000),
                              blurRadius: 4,
                              offset: Offset(0, 4))
                        ],
                      ),
                      child:
                          const Icon(Icons.add, color: Colors.white, size: 24),
                    ),
                  ),
                  Column(
                    children: [
                      Text('Your',
                          style: GoogleFonts.montserrat(
                              fontSize: 22, fontWeight: FontWeight.bold)),
                      Text('Routines',
                          style: GoogleFonts.montserrat(
                              fontSize: 22, fontWeight: FontWeight.bold)),
                    ],
                  ),
                  ClipRRect(
                    borderRadius: BorderRadius.circular(8),
                    child: Image.asset('assets/images/runny1.png',
                        width: 50, height: 60, fit: BoxFit.cover),
                  ),
                ],
              ),
            ),

            // Lista de rutinas
            Expanded(
              child: _loading
                  ? const Center(child: CircularProgressIndicator())
                  : _routines.isEmpty
                      ? Center(
                          child: Text(
                            'You don\'t have any routine.\nPress + to create one.',
                            textAlign: TextAlign.center,
                            style: GoogleFonts.montserrat(
                                fontSize: 16, color: Colors.grey),
                          ),
                        )
                      : ListView.builder(
                          padding: const EdgeInsets.symmetric(
                              horizontal: 16, vertical: 8),
                          itemCount: _routines.length,
                          itemBuilder: (context, index) {
                            final routine = _routines[index];

                            // ← exercises ahora son objetos con id y exerciseName
                            final rawExercises =
                                (routine['exercises'] as List<dynamic>?) ?? [];
                            final exerciseNames = rawExercises
                                .map((e) =>
                                    e['exerciseName']?.toString() ?? '')
                                .where((name) => name.isNotEmpty)
                                .toList();

                            return Container(
                              margin: const EdgeInsets.only(bottom: 12),
                              decoration: BoxDecoration(
                                color: Colors.white,
                                borderRadius: BorderRadius.circular(20),
                                boxShadow: const [
                                  BoxShadow(
                                    color: Color(0x20000000),
                                    blurRadius: 8,
                                    offset: Offset(0, 4),
                                    spreadRadius: 1,
                                  )
                                ],
                              ),
                              child: Padding(
                                padding: const EdgeInsets.all(16),
                                child: Column(
                                  crossAxisAlignment:
                                      CrossAxisAlignment.start,
                                  children: [
                                    Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.spaceBetween,
                                      children: [
                                        Text(
                                          routine['routineName'] ?? '',
                                          style: GoogleFonts.montserrat(
                                              fontSize: 16,
                                              fontWeight: FontWeight.bold),
                                        ),
                                        PopupMenuButton<String>(
                                          icon: const Icon(Icons.more_horiz),
                                          onSelected: (value) {
                                            if (value == 'rename')
                                              _renameRoutine(routine);
                                            if (value == 'delete')
                                              _deleteRoutine(routine);
                                          },
                                          itemBuilder: (ctx) => [
                                            const PopupMenuItem(
                                                value: 'rename',
                                                child: Text('Rename')),
                                            const PopupMenuItem(
                                                value: 'delete',
                                                child: Text('Delete',
                                                    style: TextStyle(
                                                        color: Colors.red))),
                                          ],
                                        ),
                                      ],
                                    ),

                                    // Nombres de ejercicios debajo del título
                                    if (exerciseNames.isNotEmpty)
                                      Padding(
                                        padding:
                                            const EdgeInsets.only(bottom: 12),
                                        child: Text(
                                          exerciseNames.join(', '),
                                          style: GoogleFonts.montserrat(
                                              fontSize: 12,
                                              color: Colors.grey),
                                          maxLines: 2,
                                          overflow: TextOverflow.ellipsis,
                                        ),
                                      ),

                                    const SizedBox(height: 8),

                                    // Botón Start — navega a RoutineDetailPage
                                    Container(
                                      width: double.infinity,
                                      height: 45,
                                      decoration: const BoxDecoration(
                                        gradient: LinearGradient(
                                          colors: [
                                            Color(0xFF1DB87A),
                                            Color.fromARGB(255, 28, 150, 89)
                                          ],
                                          stops: [0.0, 1.0],
                                          begin: AlignmentDirectional(1.0, 0.0),
                                          end: AlignmentDirectional(-1.0, 0),
                                        ),
                                        borderRadius: BorderRadius.all(
                                            Radius.circular(25)),
                                        boxShadow: [
                                          BoxShadow(
                                              color: Color(0x33000000),
                                              blurRadius: 4,
                                              offset: Offset(0, 4))
                                        ],
                                      ),
                                      child: TextButton(
                                        onPressed: () async {
                                          // Navega y espera que vuelva para recargar
                                          await Navigator.push(
                                            context,
                                            MaterialPageRoute(
                                              builder: (_) => RoutineDetailPage(
                                                  routine: routine),
                                            ),
                                          );
                                          // Al volver, refresca la lista
                                          _loadRoutines();
                                        },
                                        child: Text(
                                          'Start',
                                          style: GoogleFonts.montserrat(
                                            color: Colors.white,
                                            fontWeight: FontWeight.w600,
                                            fontSize: 18,
                                          ),
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            );
                          },
                        ),
            ),

            // Footer logout
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
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  PopupMenuButton<String>(
                    onSelected: (value) {
                      if (value == 'logout') _logout();
                    },
                    itemBuilder: (ctx) => [
                      const PopupMenuItem(
                        value: 'logout',
                        child: Text('Log Out',
                            style: TextStyle(color: Colors.red)),
                      ),
                    ],
                    child: Container(
                      width: 120,
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
                              offset: Offset(0, 8))
                        ],
                      ),
                      child: const Center(
                        child: Text('• • •',
                            style: TextStyle(
                                color: Colors.white,
                                fontSize: 20,
                                fontWeight: FontWeight.bold)),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}