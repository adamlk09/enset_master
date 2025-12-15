class Contact {
  final int id;
  final String name;
  final String profile;
  final String type;
  final int score;

  Contact({
    required this.id,
    required this.name,
    required this.profile,
    required this.type,
    required this.score,
  });

  factory Contact.fromJson(Map<String, dynamic> json) {
    return Contact(
/*    id: json['id'],
      name: json['name'],
      profile: json['profile'],
      type: json['type'],
      score: json['score'],*/
      id: int.tryParse(json['id'].toString()) ?? 0,
      name: json['name'] as String,
      profile: json['profile'] as String,
      type: json['type'] as String,
      score: int.tryParse(json['score'].toString()) ?? 0,
    );
  }
}