/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iotdb.cluster.query.reader;

import java.io.IOException;
import java.util.List;
import org.apache.iotdb.db.query.reader.series.IReaderByTimestamp;

public class MergedReaderByTime implements IReaderByTimestamp {

  private List<IReaderByTimestamp> innerReaders;

  public MergedReaderByTime(
      List<IReaderByTimestamp> innerReaders) {
    this.innerReaders = innerReaders;
  }

  @Override
  public Object getValueInTimestamp(long timestamp) throws IOException {
    for (IReaderByTimestamp innerReader : innerReaders) {
      if (innerReader != null) {
        Object valueInTimestamp = innerReader.getValueInTimestamp(timestamp);
        if (valueInTimestamp != null) {
          return valueInTimestamp;
        }
      }
    }
    return null;
  }
}
