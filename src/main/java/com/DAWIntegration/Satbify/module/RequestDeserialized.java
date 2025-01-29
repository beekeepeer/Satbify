package com.DAWIntegration.Satbify.module;

import java.util.List;

public record RequestDeserialized(int customerId,
                                  String token,
                                  List<Note> notes) {
}
