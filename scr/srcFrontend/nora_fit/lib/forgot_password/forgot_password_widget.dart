import '/flutter_flow/flutter_flow_theme.dart';
import '/flutter_flow/flutter_flow_util.dart';
import '/flutter_flow/flutter_flow_widgets.dart';
import '/index.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';
import 'forgot_password_model.dart';
export 'forgot_password_model.dart';
import 'package:nora_fit/services/auth_service.dart';

class ForgotPasswordWidget extends StatefulWidget {
  const ForgotPasswordWidget({super.key});

  static String routeName = 'forgotPassword';
  static String routePath = '/forgotPassword';

  @override
  State<ForgotPasswordWidget> createState() => _ForgotPasswordWidgetState();
}

class _ForgotPasswordWidgetState extends State<ForgotPasswordWidget> {
  late ForgotPasswordModel _model;
  final scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();
    _model = createModel(context, () => ForgotPasswordModel());
    _model.textFieldPasswordTextController ??= TextEditingController();
    _model.textFieldPasswordFocusNode ??= FocusNode();
    _model.textFieldEmailTextController ??= TextEditingController();
    _model.textFieldEmailFocusNode ??= FocusNode();
    _model.textFieldConfirmPasswordTextController ??= TextEditingController();
    _model.textFieldConfirmPasswordFocusNode ??= FocusNode();
  }

  @override
  void dispose() {
    _model.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        FocusScope.of(context).unfocus();
        FocusManager.instance.primaryFocus?.unfocus();
      },
      child: Scaffold(
        key: scaffoldKey,
        resizeToAvoidBottomInset: false,
        backgroundColor: FlutterFlowTheme.of(context).primaryBackground,
        body: Stack(
          children: [
            // Fondo gris
            Container(
              width: double.infinity,
              height: double.infinity,
              decoration: BoxDecoration(color: Color(0xFFDFDCDC)),
              child: Stack(
                children: [
                  Align(
                    alignment: AlignmentDirectional(0.68, -0.87),
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(8.0),
                      child: Image.asset('assets/images/runny1.png', width: 93.0, height: 129.0, fit: BoxFit.cover),
                    ),
                  ),
                  Align(
                    alignment: AlignmentDirectional(-0.43, -0.79),
                    child: Text(
                      'Forgot your \nPassword?',
                      style: FlutterFlowTheme.of(context).bodyMedium.override(
                            font: GoogleFonts.montserrat(fontWeight: FontWeight.bold),
                            fontSize: 25.0,
                            letterSpacing: 0.0,
                            fontWeight: FontWeight.bold,
                          ),
                    ),
                  ),
                ],
              ),
            ),
            // Panel blanco
            Align(
              alignment: AlignmentDirectional(0.0, 1.31),
              child: Container(
                width: double.infinity,
                height: 680.0,
                decoration: BoxDecoration(
                  color: FlutterFlowTheme.of(context).secondaryBackground,
                  boxShadow: [BoxShadow(blurRadius: 4.0, color: Color(0x33000000), offset: Offset(0.0, 2.0), spreadRadius: 4.0)],
                  borderRadius: BorderRadius.only(topLeft: Radius.circular(50.0), topRight: Radius.circular(50.0)),
                ),
                child: Stack(
                  children: [
                    // Logo
                    Align(
                      alignment: AlignmentDirectional(-0.84, -0.93),
                      child: Container(
                        width: 56.9,
                        height: 56.9,
                        decoration: BoxDecoration(
                          color: Color(0xFFDCEEF9),
                          boxShadow: [BoxShadow(blurRadius: 4.0, color: Color(0x33000000), offset: Offset(0.0, 2.0), spreadRadius: 4.0)],
                          shape: BoxShape.circle,
                        ),
                        child: Align(
                          alignment: AlignmentDirectional(0.17, 0.09),
                          child: ClipRRect(
                            borderRadius: BorderRadius.circular(8.0),
                            child: Image.asset('assets/images/NorafitPeque.png', width: 40.0, height: 44.0, fit: BoxFit.cover),
                          ),
                        ),
                      ),
                    ),

                    // Email label
                    Align(
                      alignment: AlignmentDirectional(-0.66, -0.75),
                      child: Text('Email',
                          style: FlutterFlowTheme.of(context).bodyMedium.override(
                                font: GoogleFonts.montserrat(fontWeight: FontWeight.w500),
                                fontSize: 16.0,
                                letterSpacing: 0.0,
                                fontWeight: FontWeight.w500,
                              )),
                    ),
                    // Email field
                    Align(
                      alignment: AlignmentDirectional(0.0, -0.65),
                      child: Container(
                        width: 300.0,
                        child: TextFormField(
                          controller: _model.textFieldEmailTextController,
                          focusNode: _model.textFieldEmailFocusNode,
                          autofocus: false,
                          obscureText: false,
                          decoration: InputDecoration(
                            isDense: true,
                            hintText: 'User@example.com',
                            hintStyle: FlutterFlowTheme.of(context).labelMedium.override(font: GoogleFonts.montserrat(), letterSpacing: 0.0),
                            enabledBorder: OutlineInputBorder(borderSide: BorderSide(color: Color(0x00DFDFDF), width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            focusedBorder: OutlineInputBorder(borderSide: BorderSide(color: Color(0x00000000), width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            errorBorder: OutlineInputBorder(borderSide: BorderSide(color: FlutterFlowTheme.of(context).error, width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            focusedErrorBorder: OutlineInputBorder(borderSide: BorderSide(color: FlutterFlowTheme.of(context).error, width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            filled: true,
                            fillColor: Color(0xFFDFDCDC),
                          ),
                          style: FlutterFlowTheme.of(context).bodyMedium,
                          cursorColor: FlutterFlowTheme.of(context).primaryText,
                          validator: _model.textFieldEmailTextControllerValidator.asValidator(context),
                        ),
                      ),
                    ),

                    // Create Password label
                    Align(
                      alignment: AlignmentDirectional(-0.56, -0.50),
                      child: Text('Create Password',
                          style: FlutterFlowTheme.of(context).bodyMedium.override(
                                font: GoogleFonts.montserrat(fontWeight: FontWeight.w500),
                                fontSize: 16.0,
                                letterSpacing: 0.0,
                                fontWeight: FontWeight.w500,
                              )),
                    ),
                    // Password field
                    Align(
                      alignment: AlignmentDirectional(0.0, -0.40),
                      child: Container(
                        width: 300.0,
                        child: TextFormField(
                          controller: _model.textFieldPasswordTextController,
                          focusNode: _model.textFieldPasswordFocusNode,
                          autofocus: false,
                          obscureText: !_model.textFieldPasswordVisibility,
                          decoration: InputDecoration(
                            isDense: true,
                            hintText: 'eXamPl1294+_-QW',
                            hintStyle: FlutterFlowTheme.of(context).labelMedium.override(font: GoogleFonts.montserrat(), letterSpacing: 0.0),
                            enabledBorder: OutlineInputBorder(borderSide: BorderSide(color: Color(0x00DFDFDF), width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            focusedBorder: OutlineInputBorder(borderSide: BorderSide(color: Color(0x00000000), width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            errorBorder: OutlineInputBorder(borderSide: BorderSide(color: FlutterFlowTheme.of(context).error, width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            focusedErrorBorder: OutlineInputBorder(borderSide: BorderSide(color: FlutterFlowTheme.of(context).error, width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            filled: true,
                            fillColor: Color(0xFFDFDCDC),
                            suffixIcon: InkWell(
                              onTap: () async { safeSetState(() => _model.textFieldPasswordVisibility = !_model.textFieldPasswordVisibility); },
                              focusNode: FocusNode(skipTraversal: true),
                              child: Icon(_model.textFieldPasswordVisibility ? Icons.visibility_outlined : Icons.visibility_off_outlined, size: 22),
                            ),
                          ),
                          style: FlutterFlowTheme.of(context).bodyMedium,
                          cursorColor: FlutterFlowTheme.of(context).primaryText,
                          validator: _model.textFieldPasswordTextControllerValidator.asValidator(context),
                        ),
                      ),
                    ),

                    // Confirm Password label
                    Align(
                      alignment: AlignmentDirectional(-0.56, -0.25),
                      child: Text('Confirm Password',
                          style: FlutterFlowTheme.of(context).bodyMedium.override(
                                font: GoogleFonts.montserrat(fontWeight: FontWeight.w500),
                                fontSize: 16.0,
                                letterSpacing: 0.0,
                                fontWeight: FontWeight.w500,
                              )),
                    ),
                    // Confirm Password field
                    Align(
                      alignment: AlignmentDirectional(0.0, -0.15),
                      child: Container(
                        width: 300.0,
                        child: TextFormField(
                          controller: _model.textFieldConfirmPasswordTextController,
                          focusNode: _model.textFieldConfirmPasswordFocusNode,
                          autofocus: false,
                          obscureText: !_model.textFieldConfirmPasswordVisibility,
                          decoration: InputDecoration(
                            isDense: true,
                            hintText: 'eXamPl1294+_-QW',
                            hintStyle: FlutterFlowTheme.of(context).labelMedium.override(font: GoogleFonts.montserrat(), letterSpacing: 0.0),
                            enabledBorder: OutlineInputBorder(borderSide: BorderSide(color: Color(0x00DFDFDF), width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            focusedBorder: OutlineInputBorder(borderSide: BorderSide(color: Color(0x00000000), width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            errorBorder: OutlineInputBorder(borderSide: BorderSide(color: FlutterFlowTheme.of(context).error, width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            focusedErrorBorder: OutlineInputBorder(borderSide: BorderSide(color: FlutterFlowTheme.of(context).error, width: 1.0), borderRadius: BorderRadius.circular(24.0)),
                            filled: true,
                            fillColor: Color(0xFFDFDCDC),
                            suffixIcon: InkWell(
                              onTap: () async { safeSetState(() => _model.textFieldConfirmPasswordVisibility = !_model.textFieldConfirmPasswordVisibility); },
                              focusNode: FocusNode(skipTraversal: true),
                              child: Icon(_model.textFieldConfirmPasswordVisibility ? Icons.visibility_outlined : Icons.visibility_off_outlined, size: 22),
                            ),
                          ),
                          style: FlutterFlowTheme.of(context).bodyMedium,
                          cursorColor: FlutterFlowTheme.of(context).primaryText,
                          validator: _model.textFieldConfirmPasswordTextControllerValidator.asValidator(context),
                        ),
                      ),
                    ),

                    // Botón new password
                    Align(
                      alignment: AlignmentDirectional(0.0, 0.15),
                      child: InkWell(
                        splashColor: Colors.transparent,
                        focusColor: Colors.transparent,
                        hoverColor: Colors.transparent,
                        highlightColor: Colors.transparent,
                        onTap: () async {
                          if (_model.textFieldPasswordTextController!.text !=
                              _model.textFieldConfirmPasswordTextController!.text) {
                            ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Las contraseñas no coinciden')));
                            return;
                          }
                          try {
                            await AuthService.changePassword(
                              _model.textFieldEmailTextController!.text,
                              _model.textFieldPasswordTextController!.text,
                            );
                            ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('¡Contraseña actualizada!')));
                            context.pushNamed(LogInPageWidget.routeName);
                          } catch (e) {
                            ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Error: ${e.toString()}')));
                          }
                        },
                        child: Container(
                          width: 200.0,
                          height: 45.0,
                          decoration: BoxDecoration(
                            boxShadow: [BoxShadow(blurRadius: 4.0, color: Color(0x33000000), offset: Offset(0.0, 8.0))],
                            gradient: LinearGradient(colors: [Color(0xFFC67301), Color(0xFFE39501)], stops: [0.0, 1.0], begin: AlignmentDirectional(1.0, 0.0), end: AlignmentDirectional(-1.0, 0)),
                            borderRadius: BorderRadius.circular(25.0),
                          ),
                          child: Align(
                            alignment: AlignmentDirectional(0.0, 0.0),
                            child: Text('new password!', style: GoogleFonts.montserrat(color: Color(0xFFFFF9F9), fontWeight: FontWeight.w500, fontSize: 18.0)),
                          ),
                        ),
                      ),
                    ),

                    // Texto Log In Again
                    Align(
                      alignment: AlignmentDirectional(0.0, 0.64),
                      child: Text('Log In Again',
                          style: FlutterFlowTheme.of(context).bodyMedium.override(
                                font: GoogleFonts.montserrat(fontWeight: FontWeight.w500),
                                fontSize: 16.0,
                                letterSpacing: 0.0,
                                fontWeight: FontWeight.w500,
                              )),
                    ),

                    // Botón Log In
                    Align(
                      alignment: AlignmentDirectional(0.0, 0.8),
                      child: InkWell(
                        splashColor: Colors.transparent,
                        focusColor: Colors.transparent,
                        hoverColor: Colors.transparent,
                        highlightColor: Colors.transparent,
                        onTap: () async { context.pushNamed(LogInPageWidget.routeName); },
                        child: Container(
                          width: 200.0,
                          height: 45.0,
                          decoration: BoxDecoration(
                            boxShadow: [BoxShadow(blurRadius: 4.0, color: Color(0x33000000), offset: Offset(0.0, 8.0))],
                            gradient: LinearGradient(colors: [Color(0xFF0D8FCB), Color(0xFF1CB0CC)], stops: [0.0, 1.0], begin: AlignmentDirectional(1.0, 0.0), end: AlignmentDirectional(-1.0, 0)),
                            borderRadius: BorderRadius.circular(25.0),
                          ),
                          child: Align(
                            alignment: AlignmentDirectional(0.0, 0.0),
                            child: Text('Log In', style: GoogleFonts.montserrat(color: Color(0xFFFFF9F9), fontWeight: FontWeight.w500, fontSize: 18.0)),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}