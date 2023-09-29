/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.internal.operation;

import com.mongodb.Function;
import com.mongodb.WriteConcern;
import com.mongodb.internal.TimeoutSettings;
import com.mongodb.lang.Nullable;
import org.bson.BsonDocument;

import static com.mongodb.internal.operation.CommandOperationHelper.CommandCreator;

/**
 * An operation that aborts a transaction.
 *
 * <p>This class is not part of the public API and may be removed or changed at any time</p>
 */
public class AbortTransactionOperation extends TransactionOperation {
    private BsonDocument recoveryToken;

    public AbortTransactionOperation(final TimeoutSettings timeoutSettings, final WriteConcern writeConcern) {
        super(timeoutSettings, writeConcern);
    }

    public AbortTransactionOperation recoveryToken(@Nullable final BsonDocument recoveryToken) {
        this.recoveryToken = recoveryToken;
        return this;
    }

    @Override
    protected String getCommandName() {
        return "abortTransaction";
    }

    @Override
    CommandCreator getCommandCreator() {
        CommandCreator creator = super.getCommandCreator();
        if (recoveryToken != null) {
            return (timeoutContext, serverDescription, connectionDescription) ->
                    creator.create(timeoutContext, serverDescription, connectionDescription)
                            .append("recoveryToken", recoveryToken);
        }
        return creator;
    }

    @Override
    protected Function<BsonDocument, BsonDocument> getRetryCommandModifier() {
        return cmd -> cmd;
    }
}
