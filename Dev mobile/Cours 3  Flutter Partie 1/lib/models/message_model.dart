class Message {
  final int? id;
  final int contactID;
  final DateTime dateTime;
  final String type;
  final String message;
  bool selected = false;

  Message({
    this.id,
    required this.contactID,
    required this.dateTime,
    required this.type,
    required this.message,
  });

  factory Message.fromJson(Map<String, dynamic> json) {
    return Message(
/*    id: json['id'],
      contactID: json['contactID'],
      dateTime: DateTime.parse(json['date']),
      type: json['type'],
      message: json['message'],*/
      id: int.tryParse(json['id'].toString()),
      contactID: int.tryParse(json['contactID'].toString()) ?? 0,
      dateTime: DateTime.parse(json['date'] as String),
      type: json['type'] as String,
      message: json['message'] as String,
    );
  }

  Map<String, dynamic> toJson() => {
    if (id != null) 'id': id, //just add for test cause i had prblm before
    'contactID': contactID,
    'message': message,
    'type': type,
    'date': dateTime.toIso8601String(),
  };
}