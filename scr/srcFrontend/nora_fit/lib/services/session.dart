class Session {
  static int? userId;
  static String? username;
  static String? email;

  static void save(Map<String, dynamic> user) {
    userId = user['id'];
    username = user['username'];
    email = user['email'];
  }

  static void clear() {
    userId = null;
    username = null;
    email = null;
  }
}